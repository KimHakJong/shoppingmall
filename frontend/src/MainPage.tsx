import React from 'react';
import './MainPage.css';

interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
  category: string;
}

const MainPage: React.FC = () => {
  // 임시 데이터 (실제로는 API에서 가져올 예정)
  const recommendedProducts: Product[] = [
    {
      id: 1,
      name: "추천 상품 1",
      price: 29900,
      image: "https://via.placeholder.com/200x200/FF6B6B/FFFFFF?text=추천1",
      category: "전자제품"
    },
    {
      id: 2,
      name: "추천 상품 2",
      price: 15900,
      image: "https://via.placeholder.com/200x200/4ECDC4/FFFFFF?text=추천2",
      category: "의류"
    },
    {
      id: 3,
      name: "추천 상품 3",
      price: 45900,
      image: "https://via.placeholder.com/200x200/45B7D1/FFFFFF?text=추천3",
      category: "가전"
    },
    {
      id: 4,
      name: "추천 상품 4",
      price: 8900,
      image: "https://via.placeholder.com/200x200/96CEB4/FFFFFF?text=추천4",
      category: "식품"
    }
  ];

  const popularProducts: Product[] = [
    {
      id: 5,
      name: "인기 상품 1",
      price: 19900,
      image: "https://via.placeholder.com/200x200/FFEAA7/000000?text=인기1",
      category: "전자제품"
    },
    {
      id: 6,
      name: "인기 상품 2",
      price: 12900,
      image: "https://via.placeholder.com/200x200/DDA0DD/000000?text=인기2",
      category: "의류"
    },
    {
      id: 7,
      name: "인기 상품 3",
      price: 35900,
      image: "https://via.placeholder.com/200x200/98D8C8/000000?text=인기3",
      category: "가전"
    },
    {
      id: 8,
      name: "인기 상품 4",
      price: 6900,
      image: "https://via.placeholder.com/200x200/F7DC6F/000000?text=인기4",
      category: "식품"
    }
  ];

  const formatPrice = (price: number) => {
    return price.toLocaleString('ko-KR') + '원';
  };

  return (
    <div className="main-page">
      {/* 헤더 배너 */}
      <div className="main-banner">
        <div className="banner-content">
          <h1>쇼핑몰에 오신 것을 환영합니다!</h1>
          <p>최고의 상품들을 만나보세요</p>
        </div>
      </div>

      {/* 추천 상품 섹션 */}
      <section className="product-section">
        <div className="section-header">
          <h2>추천 상품</h2>
          <p>고객님을 위한 맞춤 상품</p>
        </div>
        <div className="product-grid">
          {recommendedProducts.map((product) => (
            <div key={product.id} className="product-card">
              <div className="product-image">
                <img src={product.image} alt={product.name} />
              </div>
              <div className="product-info">
                <h3 className="product-name">{product.name}</h3>
                <p className="product-category">{product.category}</p>
                <p className="product-price">{formatPrice(product.price)}</p>
                <button className="product-button">장바구니 담기</button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* 인기 상품 섹션 */}
      <section className="product-section">
        <div className="section-header">
          <h2>인기 상품</h2>
          <p>지금 가장 인기 있는 상품들</p>
        </div>
        <div className="product-grid">
          {popularProducts.map((product) => (
            <div key={product.id} className="product-card">
              <div className="product-image">
                <img src={product.image} alt={product.name} />
                <div className="popular-badge">HOT</div>
              </div>
              <div className="product-info">
                <h3 className="product-name">{product.name}</h3>
                <p className="product-category">{product.category}</p>
                <p className="product-price">{formatPrice(product.price)}</p>
                <button className="product-button">장바구니 담기</button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* 카테고리 섹션 */}
      <section className="category-section">
        <div className="section-header">
          <h2>카테고리</h2>
          <p>원하는 카테고리를 선택하세요</p>
        </div>
        <div className="category-grid">
          <div className="category-card">
            <div className="category-icon">📱</div>
            <h3>전자제품</h3>
          </div>
          <div className="category-card">
            <div className="category-icon">👕</div>
            <h3>의류</h3>
          </div>
          <div className="category-card">
            <div className="category-icon">🏠</div>
            <h3>가전</h3>
          </div>
          <div className="category-card">
            <div className="category-icon">🍎</div>
            <h3>식품</h3>
          </div>
        </div>
      </section>
    </div>
  );
};

export default MainPage;
