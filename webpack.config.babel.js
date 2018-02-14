import webpack from 'webpack'
import path from 'path'

import UglifyJSPlugin from 'uglifyjs-webpack-plugin'

export default {
    resolve: {
        alias: {
            kotlin: path.resolve(__dirname, './web/lib/kotlin.js'),
        },
    },

    devServer: {
        contentBase: path.join(__dirname, "web"),
        compress: true,
        port: 9000
    },

    entry: "./web/lib/frontend.js",
    output: {
        filename: "./web/lib/bundle.js",
    },
    plugins: [
        new UglifyJSPlugin(),
    ],
}