// shoppingmall/frontend/src/setupProxy.js
const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
      secure: false,          // HTTPS 검증 비활성화
      protocolRewrite: 'http' // HTTP로 강제
    })
  );
};