import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Header from './Header';
import './Products.css';

// 백엔드 응답 구조에 맞춘 Product 타입 정의
interface Product {
  menuId: string;           // 메뉴 ID
  prodId: number;           // 상품 ID
  prodName: string;         // 상품명
  prodPrice: number;        // 상품가격
  imageUrl: string;         // 이미지 URL
}

// 메뉴 정보 타입 (예시, 실제 응답에 맞게 수정)
interface MenuInfo {
  menuId: string;
  menuName: string;
  menuDepth: number;
}

const Products: React.FC = () => {
  const { menuId } = useParams<{ menuId: string }>();
  const [products, setProducts] = useState<Product[]>([]);
  const [menuInfo, setMenuInfo] = useState<MenuInfo | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string>('ALL');
  const [sortBy, setSortBy] = useState<string>('default');

  // 카테고리 필터 옵션 (실제 데이터에 맞게 수정 필요)
  const categoryFilters = ['ALL', 'MIRRORS', 'OBJECT', 'VASE', 'RUG', 'HOOK&HANGER'];

  // 기본 이미지 URL들 (실제 프로젝트에 맞게 수정)
  const defaultImages = [
    'https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400&h=400&fit=crop',
    'https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?w=400&h=400&fit=crop',
    'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&h=400&fit=crop',
    'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&h=400&fit=crop'
  ];

  useEffect(() => {
    const fetchProductsByMenu = async () => {
      if (!menuId) {
        setError("메뉴 ID가 제공되지 않았습니다.");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError(null);

        // 메뉴별 상품 목록 조회
        const response = await axios.post('/api/product/list', {
          menuId: menuId
        }, {
          headers: {
            'Content-Type': 'application/json',
          },
        });

        console.log("상품 목록 조회 성공:", response.data);
        
        // ApiResponse 구조에 맞게 수정
        if (response.data.success) {
          setProducts(response.data.data || []);
          
          // 메뉴 정보도 함께 설정 (응답에서 메뉴 정보가 포함되어 있다면)
          if (response.data.data && response.data.data.length > 0 && response.data.data[0].menuInfo) {
            setMenuInfo(response.data.data[0].menuInfo);
          }
        } else {
          setError(response.data.message || "상품을 불러오는 데 실패했습니다.");
        }

      } catch (err: any) {
        console.error("메뉴별 상품 조회 실패:", err);
        setError(err.response?.data?.message || "상품을 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchProductsByMenu();
  }, [menuId]);

  // 상품 정렬 함수
  const getSortedProducts = () => {
    const sortedProducts = [...products];
    switch (sortBy) {
      case 'price-low':
        return sortedProducts.sort((a, b) => a.prodPrice - b.prodPrice);
      case 'price-high':
        return sortedProducts.sort((a, b) => b.prodPrice - a.prodPrice);
      case 'name':
        return sortedProducts.sort((a, b) => a.prodName.localeCompare(b.prodName));
      default:
        return sortedProducts;
    }
  };

  // 이미지 로딩 실패 시 대체 이미지 처리
  const handleImageError = (e: React.SyntheticEvent<HTMLImageElement, Event>, productIndex: number) => {
    const target = e.target as HTMLImageElement;
    // 기본 이미지 배열에서 순환하여 사용
    const fallbackImage = defaultImages[productIndex % defaultImages.length];
    target.src = fallbackImage;
  };

  // 이미지 URL 유효성 검사
  const getImageUrl = (product: Product, index: number) => {
    if (product.imageUrl && product.imageUrl.trim() !== '' && product.imageUrl !== 'null') {
      // 상대 경로인 경우 절대 경로로 변환
      if (product.imageUrl.startsWith('/')) {
        return `${window.location.origin}${product.imageUrl}`;
      }
      return product.imageUrl;
    }
    return defaultImages[index % defaultImages.length];
  };

  if (loading) {
    return (
      <div className="products-page-container">
        <Header />
        <div className="loading-spinner">
          <div className="spinner"></div>
          <p>상품 목록을 불러오는 중...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="products-page-container">
        <Header />
        <div className="error-message">
          <h2>오류가 발생했습니다</h2>
          <p>{error}</p>
          <button onClick={() => window.history.back()}>이전 페이지로 돌아가기</button>
        </div>
      </div>
    );
  }

  const sortedProducts = getSortedProducts();

  return (
    <div className="products-page-container">
      {/* 기존 Header 컴포넌트 사용 */}
      <Header />

      {/* 메인 콘텐츠 */}
      <main className="products-main">
        {/* 필터 섹션 */}
        <div className="filters-section">
          <div className="category-filters">
            <h3 className="current-category">DECO</h3>
            <div className="filter-options">
              {categoryFilters.map((category) => (
                <button
                  key={category}
                  className={`filter-btn ${selectedCategory === category ? 'active' : ''}`}
                  onClick={() => setSelectedCategory(category)}
                >
                  {category}
                </button>
              ))}
            </div>
          </div>
          
          <div className="products-info">
            <div className="product-count">
              {sortedProducts.length} PRODUCTS
            </div>
            <div className="sort-options">
              <select 
                value={sortBy} 
                onChange={(e) => setSortBy(e.target.value)}
                className="sort-select"
              >
                <option value="default">SORT BY</option>
                <option value="price-low">가격 낮은순</option>
                <option value="price-high">가격 높은순</option>
                <option value="name">이름순</option>
              </select>
              <button className="filter-btn">BRAND</button>
              <button className="filter-btn">DESIGNER</button>
              <button className="filter-btn">COLOR</button>
            </div>
          </div>
        </div>

        {/* 상품 그리드 */}
        <div className="product-grid">
          {sortedProducts.length > 0 ? (
            sortedProducts.map((product, index) => (
              <div key={product.prodId} className="product-card">
                <div className="product-image">
                  <img 
                    src={getImageUrl(product, index)}
                    alt={product.prodName}
                    onError={(e) => handleImageError(e, index)}
                    loading="lazy"
                  />
                </div>
                
                <div className="product-info">
                  <div className="product-brand">MUUNDO</div>
                  <h3 className="product-name">{product.prodName}</h3>
                  <div className="product-price">
                    <span className="inquiry-text">채널톡 문의</span>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <div className="no-products">
              <div className="no-products-content">
                <h3>상품이 없습니다</h3>
                <p>해당 카테고리에 등록된 상품이 없습니다.</p>
                <button onClick={() => window.history.back()}>이전 페이지로 돌아가기</button>
              </div>
            </div>
          )}
        </div>
      </main>

      {/* 플로팅 버튼 */}
      <div className="floating-buttons">
        <button className="floating-btn blog-btn">📝</button>
        <button className="floating-btn chat-btn">💬</button>
      </div>
    </div>
  );
};

export default Products;
