const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
	app.use(
		'/api',
		createProxyMiddleware({
			target: 'https://5807-211-206-46-150.jp.ngrok.io',
			changeOrigin: true,
			pathRewrite: {
				'^/api':'',
			}
		})
	);
};