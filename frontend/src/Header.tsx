import './Header.css';

/**
 * 헤더 컴포넌트
 * HPIX 쇼핑몰과 비슷한 헤더 디자인
 */
function Header() {
  return (
    <div className="header-container">
      {/* 상단 배너 */}
      <div className="top-banner">
        <span>회원가입 10% 쿠폰</span>
      </div>
      
      {/* 메인 헤더 */}
      <div className="main-header">
        <div className="header-content">
          {/* 로고 */}
          <div className="logo">
            <h1>HPIX</h1>
          </div>
          
          {/* 네비게이션 메뉴 */}
          <nav className="nav-menu">
            <ul>
              <li><a href="#">NEW</a></li>
              <li><a href="#">INSTOCK</a></li>
              <li><a href="#">FURNITURE</a></li>
              <li><a href="#">LIGHTING</a></li>
              <li><a href="#">DECO</a></li>
              <li><a href="#">TABLE WARE</a></li>
              <li><a href="#">CRAFT</a></li>
              <li><a href="#">LIVING ITEM</a></li>
              <li><a href="#">PROMOTION</a></li>
              <li><a href="#">OUTLET</a></li>
              <li><a href="#">BRAND</a></li>
            </ul>
          </nav>
          
          {/* 우측 아이콘들 */}
          <div className="header-icons">
            <button className="icon-button search-icon">
              <span>🔍</span>
            </button>
            <button className="icon-button cart-icon">
              <span>🛒</span>
              <span className="cart-count">0</span>
            </button>
            <button className="icon-button profile-icon">
              <span>👤</span>
            </button>
            <button className="icon-button menu-icon">
              <span>☰</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Header; 