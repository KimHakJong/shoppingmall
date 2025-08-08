import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './SignUp.css';

/**
 * 회원가입 화면 컴포넌트
 * 
 * 이 컴포넌트는:
 * 1. 회원구분 선택 (개인/사업자)
 * 2. 기본 정보 입력 (아이디, 비밀번호, 이름 등)
 * 3. 주소 정보 입력
 * 4. 연락처 정보 입력
 * 5. 추가 정보 입력
 * 6. 약관 동의 체크박스들
 * 7. 회원가입 버튼
 */
function SignUp() {
  const navigate = useNavigate();
  
  // ===== React State 관리 =====
  
  /** 기본 정보 */
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [name, setName] = useState('');
  
  /** 주소 정보 */
  const [zipCode, setZipCode] = useState('');
  const [address, setAddress] = useState('');
  const [detailAddress, setDetailAddress] = useState('');
  
  /** 연락처 정보 */
  const [mobile1, setMobile1] = useState('010');
  const [mobile2, setMobile2] = useState('');
  const [mobile3, setMobile3] = useState('');
  const [email, setEmail] = useState('');
  
  /** 추가 정보 */
  const [birthYear, setBirthYear] = useState('');
  const [birthMonth, setBirthMonth] = useState('');
  const [birthDay, setBirthDay] = useState('');
  const [gender, setGender] = useState('male');

  /**
   * 회원가입 폼 제출 처리 함수
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    // 필수 필드 검증
    if (!id || !password || !name) {
      alert('필수 항목을 모두 입력해주세요.');
      return;
    }
    
    // 비밀번호 확인
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    
    // 생년월일 형식 변환 (YYYYMMDD)
    const birthDate = `${birthYear}${birthMonth.padStart(2, '0')}${birthDay.padStart(2, '0')}`;
    
    // 휴대폰 번호 형식 변환 (하이픈 제거)
    const mobileNumber = `${mobile1}${mobile2}${mobile3}`;
    
    // 성별 코드 변환
    const genderCode = gender === 'male' ? 'M' : gender === 'female' ? 'F' : '';
    
    // API 요청 데이터 준비 (DB 테이블 구조에 맞춤)
    const signupData = {
      userId: id,
      userName: name,
      userPassword: password,
      userEmail: email || null,
      cellTphn: mobileNumber || null,
      dtbr: birthDate || null,
      gender: genderCode || null,
      zip: zipCode || null,
      bscs_addr: address || null,
      dtlAddr: detailAddress || null,
      userRole: 'USER' // 기본 권한
      // created_tsp, updated_tsp는 백엔드에서 자동 처리
    };
    
    try {
      // 회원가입 API 호출 (테이블 구조에 맞게 /api/users/join으로 요청)
      const response = await axios.post('/api/users/join', signupData, {
        headers: {
          'Content-Type': 'application/json',
        }
      });
      
      console.log('회원가입 성공:', response.data);
      alert('회원가입이 완료되었습니다.');
      navigate('/login'); // 로그인 페이지로 이동
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error('회원가입 실패:', error.response?.data);
        alert(`회원가입 실패: ${error.response?.data?.message || '알 수 없는 오류가 발생했습니다.'}`);
      } else {
        console.error('회원가입 API 호출 중 오류:', error);
        alert('서버 연결에 실패했습니다. 잠시 후 다시 시도해주세요.');
      }
    }
  };

  /**
   * 주소 검색 처리 함수
   */
  const handleAddressSearch = () => {
    console.log('주소 검색');
    // TODO: 주소 검색 API 연동
  };

  /**
   * 뒤로가기 처리 함수
   */
  const handleGoBack = () => {
    navigate('/login');
  };

  return (
    <div className="signup-container">
      <div className="signup-form">
        <div className="signup-header">
          <button type="button" className="back-button" onClick={handleGoBack}>
            ← 뒤로가기
          </button>
          <h1 className="signup-title">회원가입</h1>
        </div>
        
        <form onSubmit={handleSubmit}>
          {/* 기본 정보 */}
          <div className="form-section">
            <h3 className="section-title">기본 정보</h3>
            
            <div className="form-group">
              <label className="form-label">아이디</label>
              <input
                type="text"
                className="form-input"
                placeholder="아이디를 입력하세요"
                value={id}
                onChange={(e) => setId(e.target.value)}
                required
              />
              <small className="form-hint">(영문소문자/숫자, 4~16자)</small>
            </div>

            <div className="form-group">
              <label className="form-label">비밀번호</label>
              <input
                type="password"
                className="form-input"
                placeholder="비밀번호를 입력하세요"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <small className="form-hint">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16자)</small>
            </div>

            <div className="form-group">
              <label className="form-label">비밀번호 확인</label>
              <input
                type="password"
                className="form-input"
                placeholder="비밀번호를 입력하세요"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">이름</label>
              <input
                type="text"
                className="form-input"
                placeholder="이름을 입력하세요"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
          </div>

          {/* 주소 정보 */}
          <div className="form-section">
            <h3 className="section-title">주소 정보</h3>
            
            <div className="form-group">
              <label className="form-label">우편번호</label>
              <div className="zipcode-group">
                <input
                  type="text"
                  className="form-input"
                  value={zipCode}
                  onChange={(e) => setZipCode(e.target.value)}
                  readOnly
                />
                <button type="button" className="address-search-btn" onClick={handleAddressSearch}>
                  주소검색
                </button>
              </div>
            </div>

            <div className="form-group">
              <label className="form-label">기본주소</label>
              <input
                type="text"
                className="form-input"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                readOnly
              />
            </div>

            <div className="form-group">
              <label className="form-label">나머지 주소(선택 입력 가능)</label>
              <input
                type="text"
                className="form-input"
                placeholder="상세주소를 입력하세요"
                value={detailAddress}
                onChange={(e) => setDetailAddress(e.target.value)}
              />
            </div>
          </div>

          {/* 연락처 정보 */}
          <div className="form-section">
            <h3 className="section-title">연락처 정보</h3>
            
            <div className="form-group">
              <label className="form-label">휴대전화</label>
              <div className="phone-group">
                <input
                  type="text"
                  className="form-input"
                  value={mobile1}
                  onChange={(e) => setMobile1(e.target.value)}
                />
                <span className="phone-separator">-</span>
                <input
                  type="text"
                  className="form-input"
                  value={mobile2}
                  onChange={(e) => setMobile2(e.target.value)}
                />
                <span className="phone-separator">-</span>
                <input
                  type="text"
                  className="form-input"
                  value={mobile3}
                  onChange={(e) => setMobile3(e.target.value)}
                />
              </div>
            </div>

            <div className="form-group">
              <label className="form-label">이메일</label>
              <input
                type="email"
                className="form-input"
                placeholder="이메일을 입력하세요"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
          </div>

          {/* 추가 정보 */}
          <div className="form-section">
            <h3 className="section-title">추가 정보</h3>
            
            <div className="form-group">
              <label className="form-label">생년월일</label>
              <div className="birth-group">
                <input
                  type="text"
                  className="form-input"
                  placeholder="년"
                  value={birthYear}
                  onChange={(e) => setBirthYear(e.target.value)}
                />
                <span className="birth-separator">년</span>
                <input
                  type="text"
                  className="form-input"
                  placeholder="월"
                  value={birthMonth}
                  onChange={(e) => setBirthMonth(e.target.value)}
                />
                <span className="birth-separator">월</span>
                <input
                  type="text"
                  className="form-input"
                  placeholder="일"
                  value={birthDay}
                  onChange={(e) => setBirthDay(e.target.value)}
                />
                <span className="birth-separator">일</span>
              </div>
              <div className="calendar-group">
                <label className="radio-label">
                  <input
                    type="radio"
                    name="gender"
                    value="male"
                    checked={gender === 'male'}
                    onChange={(e) => setGender(e.target.value)}
                  />
                  <span>남성</span>
                </label>
                <label className="radio-label">
                  <input
                    type="radio"
                    name="gender"
                    value="female"
                    checked={gender === 'female'}
                    onChange={(e) => setGender(e.target.value)}
                  />
                  <span>여성</span>
                </label>
              </div>
            </div>
          </div>

          {/* 회원가입 버튼 */}
          <button type="submit" className="signup-button">
            회원가입
          </button>
        </form>
      </div>
    </div>
  );
}

export default SignUp;