package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.capibara.chagokchago.R
import com.capibara.chagokchago.databinding.ActivityMainBinding
import com.capibara.chagokchago.model.dto.LocationDto
import com.capibara.chagokchago.model.repository.LocationRepository
import com.capibara.chagokchago.viewmodel.KeywordViewModel
import com.capibara.chagokchago.viewmodel.MainViewModel
import com.capibara.chagokchago.viewmodel.ParkingSpaceViewModel
import com.google.android.material.navigation.NavigationView
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextStyle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private var kakaoMap: KakaoMap? = null
    private var labelLayer: LabelLayer? = null

    @Inject
    lateinit var locationSearcher: LocationRepository

    private lateinit var drawerLayout: DrawerLayout // 네비게이션 드로어
    private lateinit var errorLayout: RelativeLayout
    private lateinit var errorMessage: TextView
    private lateinit var errorDetails: TextView
    private lateinit var retryButton: ImageButton
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetAddress: TextView
    private lateinit var bottomSheetCategory: TextView
    private lateinit var bottomSheetLayout: FrameLayout
    private lateinit var searchResultLauncher: ActivityResultLauncher<Intent>

    // ViewModel을 Lazy하게 제공받기
    private val keywordViewModel: KeywordViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val parkingSpaceViewModel: ParkingSpaceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this // 생명주기 소유자 설정
        binding.viewModel = mainViewModel // MainViewModel 바인딩
        binding.keywordViewModel = keywordViewModel // Keyword ViewModel 바인딩

        // View 초기화
        initializeViews(binding)

        // DrawerLayout 초기화
        drawerLayout = findViewById(R.id.drawer_layout)
        val menuButton: ImageButton = findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            // 메뉴 버튼 클릭 시 네비게이션 드로어 열기
            drawerLayout.openDrawer(androidx.core.view.GravityCompat.START)
        }
        // NavigationView에서 메뉴 항목 클릭 리스너 설정
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_parking_ticket -> {
                    Toast.makeText(this, "주차권 선택", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_coupon -> {
                    Toast.makeText(this, "쿠폰함 선택", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_credit -> {
                    Toast.makeText(this, "충전금 선택", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ChargeLogActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_points -> {
                    Toast.makeText(this, "적립금 선택", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START)
            true
        }

        // ActivityResultLauncher 초기화
        searchResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleSearchResult(result.data)
        }

        // MapView 초기화 및 맵 라이프사이클 콜백 설정
        mapView = binding.mapView
        mapView?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                //Log.d(TAG, "Map destroyed")
            }

            override fun onMapError(error: Exception) {
                //Log.e(TAG, "Map error", error)
                showErrorScreen(error)
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                kakaoMap = map
                labelLayer = kakaoMap?.labelManager?.layer
                //Log.d(TAG, "Map is ready")
            }
        })

        // 검색창 클릭 시 검색 페이지로 이동
        binding.searchEditText.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            searchResultLauncher.launch(intent)
        }

        // 메뉴 버튼 클릭 시 네비게이션 드로어 열기
        binding.menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        // ViewModel의 마지막 마커 위치 관찰
        mainViewModel.lastMarkerPosition.observe(this) { location: LocationDto? ->
            location?.let { loc ->
                //Log.d(TAG, "Loaded last marker position: lat
                // {loc.latitude}, lon=${loc.longitude}, placeName=${loc.place}, roadAddressName=${loc.address}")
                addLabel(loc)
                val position = LatLng.from(loc.latitude, loc.longitude)
                moveCamera(position)
                updateBottomSheet(loc.place, loc.address, loc.category)
            }
        }
        // ViewModel을 통해 주차장 정보 관찰 및 마커 추가
        parkingSpaceViewModel.parkingSpaces.observe(this) { parkingSpaces ->
                    parkingSpaces?.forEach { parkingSpace ->
                        val locationDto = parkingSpace.toLocationDto() // 주차장 정보를 LocationDto로 변환
                        addLabel(locationDto) // 각 주차장 위치에 마커(라벨) 표시
                    }
                }

        // 주차장 정보 로드 호출 (앱 시작 시 지속적으로 주차장 정보 표시)
        parkingSpaceViewModel.loadParkingSpaces()
    }

    private fun initializeViews(binding: ActivityMainBinding) {
        // 에러 레이아웃 설정
        errorLayout = binding.errorLayout
        errorMessage = binding.errorMessage
        errorDetails = binding.errorDetails
        retryButton = binding.retryButton
        retryButton.setOnClickListener { onRetryButtonClick() }

        // BottomSheet 설정
        bottomSheetLayout = binding.bottomSheetLayout
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetTitle = binding.bottomSheetTitle
        bottomSheetAddress = binding.bottomSheetAddress
        bottomSheetCategory = binding.bottomSheetCategory
    }

    private fun handleSearchResult(data: Intent?) {
        if (data == null) {
            showToast("검색 결과를 받아오지 못했습니다.")
            return
        }

        // intent로 SearchActivity 데이터 받음
        val placeName = data.getStringExtra("place_name")
        val roadAddressName = data.getStringExtra("road_address_name")
        val roadCategory = data.getStringExtra("category_group_name")
        val latitude = data.getDoubleExtra("latitude", 0.0)
        val longitude = data.getDoubleExtra("longitude", 0.0)

        if (placeName == null || roadAddressName == null) {
            showToast("검색 결과가 유효하지 않습니다.")
            return
        }

        //Log.d(TAG, "Search result: $placeName, $roadAddressName, $latitude, $longitude")

        // 검색 결과 위치로 이동
        val location = roadCategory?.let { LocationDto(place = placeName, address = roadAddressName, category = it, latitude = latitude, longitude = longitude) }
        if (location != null) {
            addLabel(location)
        }
        if (location != null) {
            mainViewModel.saveLastMarkerPosition(location)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorScreen(error: Exception) {
        errorLayout.visibility = View.VISIBLE
        errorMessage.text = getString(R.string.map_error_message)
        errorDetails.text = error.message
        mapView?.visibility = View.GONE
    }

    private fun onRetryButtonClick() {
        errorLayout.visibility = View.GONE
        mapView?.visibility = View.VISIBLE
        mapView?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Log.d(TAG, "Map destroyed on retry")
            }

            override fun onMapError(error: Exception) {
                Log.e(TAG, "Map error on retry", error)
                showErrorScreen(error)
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                this@MainActivity.kakaoMap = kakaoMap
                labelLayer = kakaoMap.labelManager?.layer
                Log.d(TAG, "Map is ready on retry")
            }
        })
    }

    private fun addLabel(location: LocationDto) {
        val placeName = location.place
        val position = LatLng.from(location.latitude, location.longitude)
        val styles = kakaoMap?.labelManager?.addLabelStyles(
            LabelStyles.from(
                LabelStyle.from(R.drawable.pin).setZoomLevel(DEFAULT_ZOOM_LEVEL),
                LabelStyle.from(R.drawable.pin)
                    .setTextStyles(
                        LabelTextStyle.from(this, R.style.labelTextStyle)
                    )
                    .setZoomLevel(DEFAULT_ZOOM_LEVEL)
            )
        )

        labelLayer?.addLabel(
            LabelOptions.from(placeName, position).setStyles(styles).setTexts(placeName)
        )
        moveCamera(position)
        updateBottomSheet(placeName, location.address, location.category)

    }

    private fun moveCamera(position: LatLng) {
        kakaoMap?.moveCamera(
            CameraUpdateFactory.newCenterPosition(position),
            CameraAnimation.from(CAMERA_ANIMATION_DURATION, false, false)
        )
    }

    private fun updateBottomSheet(placeName: String, roadAddressName: String, roadCategory: String) {
        bottomSheetTitle.text = placeName
        bottomSheetAddress.text = roadAddressName
        bottomSheetCategory.text = roadCategory
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetLayout.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        mapView?.resume()
        Log.d(TAG, "MapView resumed")
    }

    override fun onPause() {
        super.onPause()
        mapView?.pause()
        Log.d(TAG, "MapView paused")
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val DEFAULT_ZOOM_LEVEL = 1
        private const val CAMERA_ANIMATION_DURATION = 10
    }
}
