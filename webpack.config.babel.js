import path from 'path'

import UglifyJSPlugin from 'uglifyjs-webpack-plugin'

export default {
    resolve: {
        modules: [
            path.resolve(__dirname, './node_modules'),
            path.resolve(__dirname, './build/kotlin-js-min/main'),
        ],
    },

    devServer: {
        contentBase: path.join(__dirname, "web"),
        compress: true,
        port: 9000
    },

    entry: path.resolve(__dirname, './build/kotlin-js-min/main/main.js'),
    output: {
        filename: "./web/lib/bundle.js",
    },
    plugins: [
        new UglifyJSPlugin(),
    ],
}