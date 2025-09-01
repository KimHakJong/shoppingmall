import './Header.css';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Sidebar from './components/Sidebar';

/**
 * 헤더 컴포넌트
 * HPIX 쇼핑몰과 비슷한 헤더 디자인
 */
function Header() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const handleMenuClick = () => {
    setIsSidebarOpen(true);
  };

  const handleCloseSidebar = () => {
    setIsSidebarOpen(false);
  };

  // ESC 키로 사이드바 닫기
  useEffect(() => {
    const handleEscKey = (event: KeyboardEvent) => {
      if (event.key === 'Escape' && isSidebarOpen) {
        setIsSidebarOpen(false);
      }
    };

    if (isSidebarOpen) {
      document.addEventListener('keydown', handleEscKey);
      // 스크롤 방지
      document.body.style.overflow = 'hidden';
    }

    return () => {
      document.removeEventListener('keydown', handleEscKey);
      document.body.style.overflow = 'unset';
    };
  }, [isSidebarOpen]);

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
            <Link to="/">
              <h1>HPIX</h1>
            </Link>
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
            <button className="icon-button menu-icon" onClick={handleMenuClick}>
              <span>☰</span>
            </button>
          </div>
        </div>
      </div>

      {/* 사이드바 컴포넌트 */}
      <Sidebar isOpen={isSidebarOpen} onClose={handleCloseSidebar} />
    </div>
  );
}

export default Header;