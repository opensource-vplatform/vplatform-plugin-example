!function(e,r){"object"==typeof exports&&"undefined"!=typeof module?r(exports,require("core-js/modules/es6.object.to-string.js"),require("core-js/modules/es6.regexp.to-string.js"),require("core-js/modules/es6.regexp.split.js"),require("core-js/modules/es6.number.constructor.js"),require("core-js/modules/es6.promise.js")):"function"==typeof define&&define.amd?define(["exports","core-js/modules/es6.object.to-string.js","core-js/modules/es6.regexp.to-string.js","core-js/modules/es6.regexp.split.js","core-js/modules/es6.number.constructor.js","core-js/modules/es6.promise.js"],r):r(((e="undefined"!=typeof globalThis?globalThis:e||self).com=e.com||{},e.com.yindangu=e.com.yindangu||{},e.com.yindangu.rule=e.com.yindangu.rule||{},e.com.yindangu.rule.demo=e.com.yindangu.rule.demo||{},e.com.yindangu.rule.demo.common={}))}(this,(function(e){"use strict";function r(e){var r,s,t,o=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"),n=new Array("","拾","佰","仟"),u=new Array("","万","亿","兆"),i=new Array("角","分","毫","厘"),c="";if(""==e)return"";if((e=parseFloat(e))>=1e15)return"";if(0==e)return c=o[0]+"元整";if(-1==(e=e.toString()).indexOf(".")?(r=e,s=""):(r=(t=e.split("."))[0],s=t[1].substr(0,4)),parseInt(r,10)>0){for(var d=0,m=r.length,l=0;l<m;l++){var p=m-l-1,a=p/4,f=p%4;"0"==(j=r.substr(l,1))?d++:(d>0&&(c+=o[0]),d=0,c+=o[parseInt(j)]+n[f]),0==f&&d<4&&(c+=u[a])}c+="元"}if(""!=s){var g=s.length;for(l=0;l<g;l++){var j;"0"!=(j=s.substr(l,1))&&(c+=o[Number(j)]+i[l])}}return""==c?c+=o[0]+"元整":""==s&&(c+="整"),c}vds.import("vds.ds.*");e.NumToChineseRule=function(e){return new Promise((function(s,t){var o=e.getInput("price"),n=e.getInput("sum");try{var u=e.newOutput(),i=e.getInput("entity"),c=e.getInput("field"),d=e.getInput("outField");if(i&&c){for(var m=e.getVObject().getInput("entity"),l=[],p=[],a=0,f=i.length;a<f;a++){var g={id:i[a].id};g[d]=r(i[a][c]),l.push(g);var j={};for(var y in i[a])j[y]=i[a][y];j[d]=r(j[c]),p.push(j)}m.updateRecords(l),u.set("entity",p)}u.set("price_cn",r(o)).set("sum_cn",r(n)),s()}catch(e){t(e)}}))},Object.defineProperty(e,"__esModule",{value:!0})}));
//# sourceMappingURL=NumToChineseRule.js.map