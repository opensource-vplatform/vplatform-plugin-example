import babel from "rollup-plugin-babel";
import { terser } from 'rollup-plugin-terser';
// rollup.config.js
export default {
  input: 'src/NumToChineseFunc.js',//源码主入口路径
  output: {
    file: 'dist/NumToChineseFunc.js',//打包输出路径
    format:'umd',//编译出umd格式
    name:'com.yindangu.func.demo',//定义全局命名空间
    sourcemap:true
  },
  plugins: [
    babel({ runtimeHelpers: true }),//babel转换
    terser()//脚本压缩
  ]
};