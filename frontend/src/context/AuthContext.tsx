import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface AuthContextType {
  isLoggedIn: boolean;
  login: (accessToken: string, refreshToken: string, userId: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(() => {
    // localStorage에서 초기 로그인 상태를 확인합니다.
    return !!localStorage.getItem('accessToken');
  });

  // 로그인 시 호출될 함수
  const login = (accessToken: string, refreshToken: string, userId: string) => {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('userId', userId);
    setIsLoggedIn(true);
  };

  // 로그아웃 시 호출될 함수
  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
    setIsLoggedIn(false);
  };

  // JWT 토큰 만료 등 비동기적인 로그인 상태 변경을 감지할 필요가 있다면 useEffect를 사용할 수 있습니다.
  // 예를 들어, 페이지 로드 시 토큰 유효성을 검사하는 로직을 여기에 추가할 수 있습니다.
  useEffect(() => {
    const checkAuthStatus = () => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        // 여기에서 토큰 유효성 검사 로직 (예: 서버에 토큰 전송하여 유효성 확인)을 추가할 수 있습니다.
        // 현재는 토큰 존재 여부만으로 로그인 상태를 판단합니다.
        setIsLoggedIn(true);
      } else {
        setIsLoggedIn(false);
      }
    };
    window.addEventListener('storage', checkAuthStatus); // localStorage 변경 감지
    return () => {
      window.removeEventListener('storage', checkAuthStatus);
    };
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// AuthContext를 사용하기 위한 커스텀 훅
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
