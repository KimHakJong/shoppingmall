import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Header from './Header';
import MainPage from './MainPage';
import Products from './Products'; // Products 컴포넌트 임포트
import NaverLogin from './components/NaverLoginCallback'; // 네이버 로그인 컴포넌트 임포트
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <div className="main-content">
          <Routes>
            <Route path="/" element={<MainPage />} /> {/* 메인 페이지는 / 경로 유지 */}
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            {/* 네이버 로그인 라우트 */}
            <Route path="/naverlogincollback" element={<NaverLogin />} />
            {/* 동적 메뉴 ID를 처리하는 라우트 추가 */}
            <Route path="/products/:menuId" element={<Products />} /> {/* Products 컴포넌트 사용 */}
            <Route path="/mypage" element={<MainPage />} /> {/* 임시 마이페이지 라우트 */}
            <Route path="/cart" element={<MainPage />} /> {/* 임시 장바구니 라우트 */}
            <Route path="/admin" element={<MainPage />} /> {/* 관리자설정 라우트 */}
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
