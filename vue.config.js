
module.exports = {
    devServer: {
        proxy: {
            '/brands': {
                target: 'http://localhost', // 后端服务地址
                changeOrigin: true
            },
            '/users': {
                target: 'http://localhost', // 后端服务地址
                changeOrigin: true
            },
            '/address': {
                target: 'http://localhost', // 后端服务地址
                changeOrigin: true
            },
            '/orders': {
                target: 'http://localhost', // 后端服务地址
                changeOrigin: true
            },
        },
    }
};