import { Link } from 'react-router-dom';
import './Sidebar.css';

interface SidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

/**
 * 사이드바 메뉴 컴포넌트
 * HPIX 스타일의 사이드바 메뉴
 */
function Sidebar({ isOpen, onClose }: SidebarProps) {
  if (!isOpen) return null;

  return (
    <div className="sidebar-overlay" onClick={onClose}>
      <div className="sidebar-menu" onClick={(e) => e.stopPropagation()}>
        {/* 사이드바 헤더 */}
        <div className="sidebar-header">
          <button className="close-button" onClick={onClose}>
            CLOSE
          </button>
        </div>

        {/* 사이드바 메뉴 내용 */}
        <div className="sidebar-content">
          {/* SHOP 섹션 */}
          <div className="sidebar-section">
            <h3 className="section-title">SHOP</h3>
            <ul className="sidebar-menu-list">
              <li><a href="#" onClick={onClose}>NEW</a></li>
              <li><a href="#" onClick={onClose}>INSTOCK</a></li>
              <li><a href="#" onClick={onClose}>FURNITURE</a></li>
              <li><a href="#" onClick={onClose}>LIGHTING</a></li>
              <li><a href="#" onClick={onClose}>DECO</a></li>
              <li><a href="#" onClick={onClose}>TABLE WARE</a></li>
              <li><a href="#" onClick={onClose}>CRAFT</a></li>
              <li><a href="#" onClick={onClose}>LIVING ITEM</a></li>
              <li><a href="#" onClick={onClose}>AGO LIGHTING</a></li>
              <li><a href="#" onClick={onClose}>BETTER ELEMENTS STUDIO</a></li>
              <li><a href="#" onClick={onClose}>OUTLET</a></li>
            </ul>
          </div>

          {/* PROMOTION 섹션 */}
          <div className="sidebar-section">
            <h3 className="section-title">PROMOTION</h3>
          </div>

          {/* BRAND 섹션 */}
          <div className="sidebar-section">
            <h3 className="section-title">BRAND</h3>
          </div>

          {/* HPIX STORY 섹션 */}
          <div className="sidebar-section">
            <h3 className="section-title">HPIX STORY</h3>
          </div>

          {/* LOGIN 섹션 */}
          <div className="sidebar-section">
            <Link to="/login" className="sidebar-login-link" onClick={onClose}>
              LOGIN
            </Link>
          </div>

          {/* WISHLIST 섹션 */}
          <div className="sidebar-section">
            <span className="sidebar-wishlist">WISHLIST (0)</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;
