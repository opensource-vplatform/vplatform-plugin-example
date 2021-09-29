  /*
 * 数字转汉字
 * 规则编号：NumToChineseRule
 * 规则配置信息:
 * 1、entityCode 实体编号
 */
vds.import("vds.ds.*");
function convertCurrency(money) {
	//汉字的数字
	var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
	//基本单位
	var cnIntRadice = new Array('', '拾', '佰', '仟');
	//对应整数部分扩展单位
	var cnIntUnits = new Array('', '万', '亿', '兆');
	//对应小数部分单位
	var cnDecUnits = new Array('角', '分', '毫', '厘');
	//整数金额时后面跟的字符
	var cnInteger = '整';
	//整型完以后的单位
	var cnIntLast = '元';
	//最大处理的数字
	var maxNum = 999999999999999.9999;
	//金额整数部分
	var integerNum;
	//金额小数部分
	var decimalNum;
	//输出的中文金额字符串
	var chineseStr = '';
	//分离金额后用的数组，预定义
	var parts;
	if (money == '') {
		return '';
	}
	money = parseFloat(money);
	if (money >= maxNum) {
		//超出最大处理数字
		return '';
	}
	if (money == 0) {
		chineseStr = cnNums[0] + cnIntLast + cnInteger;
		return chineseStr;
	}
	//转换为字符串
	money = money.toString();
	if (money.indexOf('.') == -1) {
		integerNum = money;
		decimalNum = '';
	} else {
		parts = money.split('.');
		integerNum = parts[0];
		decimalNum = parts[1].substr(0, 4);
	}
	//获取整型部分转换
	if (parseInt(integerNum, 10) > 0) {
		var zeroCount = 0;
		var IntLen = integerNum.length;
		for (var i = 0; i < IntLen; i++) {
			var n = integerNum.substr(i, 1);
			var p = IntLen - i - 1;
			var q = p / 4;
			var m = p % 4;
			if (n == '0') {
				zeroCount++;
			} else {
				if (zeroCount > 0) {
					chineseStr += cnNums[0];
				}
				//归零
				zeroCount = 0;
				chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
			}
			if (m == 0 && zeroCount < 4) {
				chineseStr += cnIntUnits[q];
			}
		}
		chineseStr += cnIntLast;
	}
	//小数部分
	if (decimalNum != '') {
		var decLen = decimalNum.length;
		for (var i = 0; i < decLen; i++) {
			var n = decimalNum.substr(i, 1);
			if (n != '0') {
				chineseStr += cnNums[Number(n)] + cnDecUnits[i];
			}
		}
	}
	if (chineseStr == '') {
		chineseStr += cnNums[0] + cnIntLast + cnInteger;
	} else if (decimalNum == '') {
		chineseStr += cnInteger;
	}
	return chineseStr;
}
let NumToChineseRule = function (ruleContext) {
    return new Promise((resolve,reject)=>{
		//获取规则输入参数
		let num1 = ruleContext.getInput("price");
		let num2 = ruleContext.getInput("sum");
		try{
			var output = ruleContext.newOutput();
			let datas = ruleContext.getInput("entity");
			let field = ruleContext.getInput("field");
			let outField = ruleContext.getInput("outField");
			if(datas && field){
				var ds = ruleContext.getVObject().getInput("entity");
				var updateRecords = [];
				var outRecords = [];
				for(var i = 0,len = datas.length;i<len;i++){
					var map = {
						id:datas[i].id
					}
					map[outField] = convertCurrency(datas[i][field]);
					updateRecords.push(map);
					var od = {}
					for(var key in datas[i]){
						od[key] = datas[i][key];
					}
					od[outField] = convertCurrency(od[field]);
					outRecords.push(od)
				}
				//更新到输入实体
				ds.updateRecords(updateRecords);
				//设置到输出实体
				output.set("entity", outRecords);
			}
			//设置规则输出参数
			output.set("price_cn", convertCurrency(num1)).set("sum_cn", convertCurrency(num2));

			resolve();
		}catch(e){
			reject(e);
		}
	});
};

export {
	NumToChineseRule
}