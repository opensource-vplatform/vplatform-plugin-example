!function(e,r){"object"==typeof exports&&"undefined"!=typeof module?r(exports,require("core-js/modules/es6.object.to-string.js"),require("core-js/modules/es6.regexp.to-string.js"),require("core-js/modules/es6.regexp.split.js"),require("core-js/modules/es6.number.constructor.js")):"function"==typeof define&&define.amd?define(["exports","core-js/modules/es6.object.to-string.js","core-js/modules/es6.regexp.to-string.js","core-js/modules/es6.regexp.split.js","core-js/modules/es6.number.constructor.js"],r):r(((e="undefined"!=typeof globalThis?globalThis:e||self).com=e.com||{},e.com.yindangu=e.com.yindangu||{},e.com.yindangu.func=e.com.yindangu.func||{},e.com.yindangu.func.demo={}))}(this,(function(e){"use strict";vds.import("vds.ds.*");e.evaluate=function(e){return function(e){var r,s,o,n=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"),t=new Array("","拾","佰","仟"),u=new Array("","万","亿","兆"),i=new Array("角","分","毫","厘"),c="";if(""==e)return"";if((e=parseFloat(e))>=1e15)return"";if(0==e)return n[0]+"元整";if(-1==(e=e.toString()).indexOf(".")?(r=e,s=""):(r=(o=e.split("."))[0],s=o[1].substr(0,4)),parseInt(r,10)>0){for(var d=0,f=r.length,a=0;a<f;a++){var l=f-a-1,m=l/4,j=l%4;"0"==(g=r.substr(a,1))?d++:(d>0&&(c+=n[0]),d=0,c+=n[parseInt(g)]+t[j]),0==j&&d<4&&(c+=u[m])}c+="元"}if(""!=s){var p=s.length;for(a=0;a<p;a++){var g;"0"!=(g=s.substr(a,1))&&(c+=n[Number(g)]+i[a])}}return""==c?c+=n[0]+"元整":""==s&&(c+="整"),c}(e)},Object.defineProperty(e,"__esModule",{value:!0})}));
//# sourceMappingURL=NumToChineseFunc.js.map
