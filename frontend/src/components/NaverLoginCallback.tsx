import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

/**
 * 네이버 로그인 컴포넌트
 * 네이버 OAuth 로그인을 처리하고 JWT 토큰을 받아서 저장
 */
const NaverLogin: React.FC = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  /**
   * 컴포넌트 마운트 시 URL 파라미터에서 네이버 인증 코드 확인
   * 네이버 로그인 후 리다이렉트된 경우 인증 코드가 URL에 포함됨
   */
  useEffect(() => {
    // URL에서 인증 코드와 상태 값 추출
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const state = urlParams.get('state');
    const error = urlParams.get('error');
    console.log('네이버 로그인 처리 시작 - code:', code);
    // 에러가 있는 경우 처리
    if (error) {
      setError('네이버 로그인 중 오류가 발생했습니다: ' + error);
      return;
    }

    // 인증 코드가 있는 경우 백엔드로 전송하여 로그인 처리
    if (code) {
      handleNaverLogin(code, state);
    }
  }, []);

  /**
   * 네이버 로그인 처리
   * 인증 코드를 백엔드로 전송하여 JWT 토큰을 발급받음
   * 
   * @param code 네이버 OAuth 인증 코드
   * @param state CSRF 방지를 위한 상태 토큰
   */
  const handleNaverLogin = async (code: string, state: string | null) => {
    try {
      setIsLoading(true);
      setError(null);

      console.log('네이버 로그인 처리 시작 - code:', code);

      // 백엔드로 인증 코드 전송
      const response = await axios.post('/api/auth/naver/login', {
        code: code,
        state: state
      }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      console.log('네이버 로그인 응답:', response.data);

      // 응답이 성공인 경우 JWT 토큰 저장
      if (response.data.success) {
        const loginData = response.data.data;
        
        // JWT 토큰을 로컬 스토리지에 저장
        localStorage.setItem('accessToken', loginData.accessToken);
        localStorage.setItem('refreshToken', loginData.refreshToken);
        localStorage.setItem('userId', loginData.userId);
        localStorage.setItem('userEmail', loginData.email);
        localStorage.setItem('loginType', 'NAVER');

        console.log('네이버 로그인 성공 - 사용자 정보 저장 완료');

        // 로그인 성공 후 메인 페이지로 리다이렉트
        navigate('/');
        
      } else {
        setError(response.data.message || '네이버 로그인에 실패했습니다.');
        navigate('/login');
      }

    } catch (err: any) {
      console.error('네이버 로그인 오류:', err);
      setError(err.response?.data?.message || '네이버 로그인 중 오류가 발생했습니다.');
      navigate('/login');
    } finally {
      setIsLoading(false);
    }
  };

  /**
   * 네이버 로그인 페이지로 리다이렉트
   * 사용자가 네이버 로그인 버튼을 클릭했을 때 호출
   */
  const redirectToNaverLogin = async () => {
    try {
      setIsLoading(true);
      setError(null);

      // 백엔드에서 네이버 로그인 URL 요청
      const response = await axios.get('/api/auth/naver/login-url');
      
      if (response.data.success) {
        // 네이버 로그인 페이지로 리다이렉트
        window.location.href = response.data.data;
      } else {
        setError('네이버 로그인 URL을 가져오는데 실패했습니다.');
      }

    } catch (err: any) {
      console.error('네이버 로그인 URL 요청 오류:', err);
      setError('네이버 로그인 URL을 가져오는데 실패했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  /**
   * 로딩 중일 때 표시할 컴포넌트
   */
  if (isLoading) {
    return (
      <div className="naver-login-container">
        <div className="loading-spinner">
          <div className="spinner"></div>
          <p>네이버 로그인 처리 중...</p>
        </div>
      </div>
    );
  }

  /**
   * 에러가 있을 때 표시할 컴포넌트
   */
  if (error) {
    return (
      <div className="naver-login-container">
        <div className="error-message">
          <h2>로그인 오류</h2>
          <p>{error}</p>
          <button onClick={() => window.location.href = '/'}>
            메인 페이지로 돌아가기
          </button>
        </div>
      </div>
    );
  }

  /**
   * 네이버 로그인 버튼 컴포넌트
   */
  return (
    <div className="naver-login-container">
      <div className="login-card">
        <h2>네이버로 로그인</h2>
        <p>네이버 계정으로 간편하게 로그인하세요.</p>
        
        <button 
          className="naver-login-btn"
          onClick={redirectToNaverLogin}
          disabled={isLoading}
        >
          <img 
            src="https://developers.naver.com/assets/img/btnG_완성형.png" 
            alt="네이버 로그인"
            className="naver-logo"
          />
          네이버로 로그인
        </button>
        
        <div className="login-links">
          <a href="/login">이메일로 로그인</a>
          <span> | </span>
          <a href="/signup">회원가입</a>
        </div>
      </div>
    </div>
  );
};

export default NaverLogin;
