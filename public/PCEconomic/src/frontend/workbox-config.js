const path = require("path");

module.exports = {
    globDirectory: '../main/resources/{static,templates}/',
    globPatterns: [
        '**/*.{html,json,js,css,png,jpg,gif,svg,ico}'
    ],
    swDest: "sw.js",
    clientsClaim: true,
    skipWaiting: true,
    runtimeCaching: [{
        urlPattern: /\.(?:png|jpg|jpeg|svg|gif)$/,
        handler: 'CacheFirst',
        options: {
            cacheName: 'images',
            expiration: {
                maxEntries: 10,
                maxAgeSeconds: 7 * 24 * 60 * 60, // 1 week
            },
        },
    }],
    modifyURLPrefix: {
        '/static': '',
        '/templates': ''
    }
};