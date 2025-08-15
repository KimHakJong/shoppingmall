import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Login.css';

/**
 * 로그인 화면 컴포넌트
 * 
 * 이 컴포넌트는:
 * 1. ID/비밀번호 입력 폼
 * 2. 아이디 저장 체크박스
 * 3. 로그인 버튼
 * 4. 회원가입/아이디찾기/비밀번호찾기 링크
 * 5. 네이버/카카오 소셜 로그인 버튼
 */
function Login() {
  const navigate = useNavigate();
  
  // ===== React State 관리 =====
  
  /** ID 입력값 */
  const [id, setId] = useState('');
  
  /** 비밀번호 입력값 */
  const [password, setPassword] = useState('');
  
  /** 아이디 저장 체크박스 상태 */
  const [rememberId, setRememberId] = useState(false);

  /**
   * 로그인 폼 제출 처리 함수
   * 
   * @param e 폼 제출 이벤트
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // 프론트엔드에서 LoginRequest DTO에 맞춰서 전송
    // (백엔드에서 LoginRequest DTO가 userId, userPassword만 받는다면 아래와 같이 전송)
    // 만약 추가적으로 필요한 값이 있다면(예: rememberMe 등) 여기에 추가
    const loginData = {
      userId: id,
      userPassword: password
      // 예시: rememberMe: rememberId
    };

    try {
      const response = await axios.post('/api/users/login', loginData, {
        headers: {
          'Content-Type': 'application/json',
        }
      });

      // 로그인 성공 처리
      console.log('로그인 성공:', response.data);
      alert('로그인에 성공하였습니다.');
      // TODO: 토큰 저장, 메인 페이지 이동 등 추가 처리
      // 예시: navigate('/'); 
    } catch (error: any) {
      if (axios.isAxiosError(error)) {
        console.error('로그인 실패:', error.response?.data);
        alert(`로그인 실패: ${error.response?.data?.message || '알 수 없는 오류가 발생했습니다.'}`);
      } else {
        console.error('로그인 API 호출 중 오류:', error);
        alert('서버 연결에 실패했습니다. 잠시 후 다시 시도해주세요.');
      }
    }
  };

  /**
   * 네이버 로그인 처리 함수
   */
  const handleNaverLogin = () => {
    console.log('네이버 로그인 시도');
    // TODO: 네이버 OAuth 로그인 구현
  };

  /**
   * 카카오 로그인 처리 함수
   */
  const handleKakaoLogin = () => {
    console.log('카카오 로그인 시도');
    // TODO: 카카오 OAuth 로그인 구현
  };

  /**
   * 회원가입 페이지로 이동
   */
  const handleSignUp = () => {
    console.log('회원가입 페이지로 이동');
    navigate('/signup');
  };

  /**
   * 아이디 찾기 페이지로 이동
   */
  const handleFindId = () => {
    console.log('아이디 찾기 페이지로 이동');
    // TODO: 아이디 찾기 페이지 라우팅
  };

  /**
   * 비밀번호 찾기 페이지로 이동
   */
  const handleFindPassword = () => {
    console.log('비밀번호 찾기 페이지로 이동');
    // TODO: 비밀번호 찾기 페이지 라우팅
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h1 className="login-title">로그인</h1>
        
        <form onSubmit={handleSubmit}>
          {/* ID 입력 필드 */}
          <div className="form-group">
            <label htmlFor="id" className="form-label">아이디</label>
            <input
              type="text"
              id="id"
              className="form-input"
              placeholder="아이디를 입력하세요"
              value={id}
              onChange={(e) => setId(e.target.value)}
              required
            />
          </div>

          {/* 비밀번호 입력 필드 */}
          <div className="form-group">
            <label htmlFor="password" className="form-label">비밀번호</label>
            <input
              type="password"
              id="password"
              className="form-input"
              placeholder="비밀번호를 입력하세요"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          {/* 아이디 저장 체크박스 */}
          <div className="form-group checkbox-group">
            <label className="checkbox-label">
              <input
                type="checkbox"
                checked={rememberId}
                onChange={(e) => setRememberId(e.target.checked)}
                className="checkbox-input"
              />
              <span className="checkbox-text">아이디 저장</span>
            </label>
          </div>

          {/* 로그인 버튼 */}
          <button type="submit" className="login-button">
            로그인
          </button>
        </form>

        {/* 계정 관리 링크 */}
        <div className="account-links">
          <button type="button" className="link-button" onClick={handleSignUp}>
            회원가입
          </button>
          <span className="link-separator">|</span>
          <button type="button" className="link-button" onClick={handleFindId}>
            아이디찾기
          </button>
          <span className="link-separator">|</span>
          <button type="button" className="link-button" onClick={handleFindPassword}>
            비밀번호찾기
          </button>
        </div>

        {/* 소셜 로그인 버튼들 */}
        <div className="social-login">
          <button 
            type="button" 
            className="social-button naver-button"
            onClick={handleNaverLogin}
          >
            <div className="social-icon naver-icon">N</div>
            <span>네이버 로그인</span>
          </button>
          
          <button 
            type="button" 
            className="social-button kakao-button"
            onClick={handleKakaoLogin}
          >
            <div className="social-icon kakao-icon">💬</div>
            <span>카카오 로그인</span>
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login; 