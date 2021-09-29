var NumToChinese = function(num,cb){
    var n = num;
    vds.config({
        debug:true
    });
    vds.import("vds.mock.*","vds.rpc.*");
    vds.ready(function(){
        //vds.rpc.get("../manifest.json").then(function(metadata){
        let metadata = {
            "groupId": "com.yindangu.vplatform.client.function",
            "code": "NumToChineseFunc",
            "plugins": [{
                "type":"function",
                "scope":"client",
                "code":"NumToChineseFunc",
                "name":"数字转大写汉字(前端)",
                "desc":"数字转大写汉字(前端)",
                "entry":"com.yindangu.func.demo.evaluate",
                "defineUrl":"./dist/NumToChineseFunc.js",
                "debugUrl":"./debug/function.js",
                "inputs":[{
                    "index":"",
                    "type":"number",
                    "desc":"转换的数字",
                    "required":false,
                    "default":null
                }],
                "output":{
                    "type":"char",
                    "desc":"数字转大写汉字",
                    "unknowType":false
                }
            }]
        };
        vds.mock.init(metadata).then(function(mock){
            mock.get("NumToChineseFunc").then(function(funcMock){
                funcMock.mockInputs(n);
                var num = funcMock.exec();
                cb(num);
            }).catch(function(error){
                alert(error.message);
                throw error;
            });
        }).catch(function(error){
            alert(error.message);
            throw error;
        });
    });
}
