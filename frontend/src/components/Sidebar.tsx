import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './Sidebar.css';
import { useAuth } from '../context/AuthContext'; // AuthContext 가져오기

interface SidebarProps {
  isOpen: boolean;
  onClose: () => void;
}

interface MenuItem {
  menuId: string;
  menuName: string;
  menuDepth: number; // 추가: 메뉴의 깊이
  // 필요에 따라 추가 필드 작성
}

function Sidebar({ isOpen, onClose }: SidebarProps) {
  const [menuList, setMenuList] = useState<MenuItem[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { isLoggedIn, logout } = useAuth(); // AuthContext에서 isLoggedIn과 logout 가져오기

  const handleLogout = async () => {
    try {
      const refreshToken = localStorage.getItem('refreshToken');
      const accessToken = localStorage.getItem('accessToken');
      const userId = localStorage.getItem('userId');
      await axios.post('/api/users/logout', { userId, refreshToken }, {
        headers: {
          'Content-Type': 'application/json',
          ...(accessToken ? { Authorization: `Bearer ${accessToken}` } : {})
        }
      });
      logout();
      onClose();
    } catch (e) {
      // 실패해도 클라이언트 토큰은 제거하여 안전하게 로그아웃 처리
      logout();
      onClose();
    }
  };

  useEffect(() => {
    if (isOpen) {
      console.log("사이드바가 열렸습니다. API 호출 시작..."); // 로그 추가
      setLoading(true);
      setError(null);
      axios.post('/api/menus/searchall', {}, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then((response) => {
          console.log("API 호출 성공:", response.data); // 성공 로그
          
          // ApiResponse 구조에 맞게 수정
          if (response.data.success) {
            setMenuList(response.data.data || []);
          } else {
            setError(response.data.message || '메뉴를 불러오지 못했습니다.');
          }
        })
        .catch((err) => {
          console.error("API 호출 실패:", err);
          console.error("에러 상세:", err.response);
          console.error("에러 응답 데이터:", err.response?.data);
          console.error("에러 상태 코드:", err.response?.status);
          
          // ApiResponse 구조에 맞게 수정
          if (err.response?.data?.message) {
            setError(err.response.data.message);
          } else {
            setError('메뉴를 불러오지 못했습니다. 상태 코드: ' + err.response?.status);
          }
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="sidebar-overlay" onClick={onClose}>
      <div className="sidebar-menu" onClick={(e) => e.stopPropagation()}>
        <div className="sidebar-header">
          <button className="close-button" onClick={onClose}>
            CLOSE
          </button>
        </div>

        <div className="sidebar-content">
          {/* SHOP 섹션 */}
          <div className="sidebar-section">
            {loading ? (
              <div className="sidebar-loading">메뉴를 불러오는 중...</div>
            ) : error ? (
              <div className="sidebar-error">{error}</div>
            ) : (
              <ul className="sidebar-menu-list">
                {menuList.length > 0 ? (
                  menuList.map((menu) => (
                                         <li key={menu.menuId} style={{ paddingLeft: `${(menu.menuDepth - 1) * 20}px` }}>
                                               <Link 
                          to={`/products/${menu.menuId}`} 
                          onClick={onClose} 
                          className={menu.menuDepth === 1 ? 'depth-1-menu' : ''}
                         style={{ 
                           display: 'block', 
                           padding: '12px 15px',
                           textDecoration: 'none',
                           color: '#333',
                           transition: 'all 0.3s ease',
                           borderRadius: '8px'
                         }}
                         onMouseEnter={(e) => {
                           e.currentTarget.style.backgroundColor = '#f8f9fa';
                           e.currentTarget.style.transform = 'translateX(5px)';
                         }}
                         onMouseLeave={(e) => {
                           e.currentTarget.style.backgroundColor = 'transparent';
                           e.currentTarget.style.transform = 'translateX(0)';
                         }}
                       >
                         {menu.menuName}
                       </Link>
                     </li>
                  ))
                ) : (
                  <li>메뉴가 없습니다.</li>
                )}
              </ul>
            )}
          </div>

          {isLoggedIn ? (
            <div className="sidebar-section">
              <Link to="/" onClick={handleLogout} className="sidebar-login-link depth-1-menu">
                로그아웃
              </Link>
            </div>
          ) : (
            <>
              {/* 로그인 섹션 */}
              <div className="sidebar-section">
                <Link to="/login" onClick={onClose} className="sidebar-login-link depth-1-menu">
                  로그인
                </Link>
              </div>

              {/* 회원가입 섹션 */}
              <div className="sidebar-section">
                <Link to="/signup" onClick={onClose} className="sidebar-login-link depth-1-menu">
                  회원가입
                </Link>
              </div>
            </>
          )}

          {/* 마이페이지 섹션 */}
          <div className="sidebar-section">
            <Link to="/mypage" onClick={onClose} className="sidebar-login-link depth-1-menu">
              마이페이지
            </Link>
          </div>

          {/* 장바구니 섹션 */}
          <div className="sidebar-section">
            <Link to="/cart" onClick={onClose} className="sidebar-login-link depth-1-menu">
              장바구니
            </Link>
          </div>

         {/* 관리자설정 섹션 */}
         <div className="sidebar-section">
            <Link to="/admin" onClick={onClose} className="sidebar-login-link depth-1-menu">
              관리자설정
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;