package com.yindangu.plugin.demo;import java.util.Arrays;import java.util.List;import com.yindangu.plugin.demo.command.ExampleMyHttpCommand;import com.yindangu.plugin.demo.command.PluginIndexCommand;import com.yindangu.plugin.demo.function.NumberUpperFunc;import com.yindangu.plugin.demo.rule.NumberUpperRolue;import com.yindangu.plugin.demo.rule.NumberUpperRolueEntity;import com.yindangu.plugin.demo.system.startup.SystemStartupDemo;import com.yindangu.plugin.demo.system.startup.SystemStartupExample;import com.yindangu.plugin.demo.timer.TimerTaskCommand;import com.yindangu.v3.formula.rule.inte.DeleteRecordById;import com.yindangu.v3.formula.rule.inte.UpdateRecord;import com.yindangu.v3.plugin.vds.reg.api.IRegisterPlugin;import com.yindangu.v3.plugin.vds.reg.api.builder.IEditorBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.IEntityBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.IFileStorageServiceBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.IFunctionBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.IHttpCommandBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.IRuleBuilder;import com.yindangu.v3.plugin.vds.reg.api.builder.ISystemStartupBuilder;import com.yindangu.v3.plugin.vds.reg.api.model.EditorType;import com.yindangu.v3.plugin.vds.reg.api.model.IComponentProfileVo;import com.yindangu.v3.plugin.vds.reg.api.model.IFileStorageServiceProfileVo;import com.yindangu.v3.plugin.vds.reg.api.model.IPluginProfileVo;import com.yindangu.v3.plugin.vds.reg.api.model.ISystemStartupProfileVo;import com.yindangu.v3.plugin.vds.reg.api.model.VariableType;import com.yindangu.v3.plugin.vds.reg.common.RegVds;/** * 构件注册器(注册入口) *  * @author jiqj * */public class MyRegisterPlug implements IRegisterPlugin {	public static final String D_GroupId="com.yindangu.plugin",D_Component="mydemo";	@Override	public IComponentProfileVo getComponentProfile() {		return RegVds.getPlugin().getComponentProfile()				.setGroupId(D_GroupId)//机构id(groupId)不能修改				.setCode(D_Component)//构件编码不能修改				.build();	}	private IPluginProfileVo getFileStorageService() {		IFileStorageServiceBuilder fss = RegVds.getPlugin().getFileStorageServicePlugin();		IFileStorageServiceProfileVo vo = fss.setName("我的文件存储")				.setCode(MyFileStorageService.FileStorage_Code) //插件编码不能修改				.setDesc("我的文件存储服务") //只能修改描述的信息				.setConfigPath("./config.xml")				.setAuthor("徐刚")				.setEntry(MyFileStorageService.class)				.build();		return vo;	}		@Override	public List<IPluginProfileVo> getPluginProfile() {		IPluginProfileVo func = getNumberUpperFunc();		IPluginProfileVo ruleValue = getNumberUpperRolueValue();		IPluginProfileVo ruleEntiry = getNumberUpperRolueEntity();		IPluginProfileVo cmd = getHttpCommand();				IPluginProfileVo updateEntity = getUpdateDataView();		IPluginProfileVo deleteById = getDeleteRecordById();		IPluginProfileVo fss = getFileStorageService();				IPluginProfileVo indexCommand = getHttpIndexCommand();		IPluginProfileVo taskCommand = getTimerTaskCommand();		IPluginProfileVo systemStartup = getSystemStartup();		IPluginProfileVo startupDemo = getSystemStartupDemo();		return Arrays.asList(func, ruleValue, ruleEntiry, cmd,				updateEntity, deleteById , fss ,indexCommand,				taskCommand,systemStartup,startupDemo);	}	/** 函数元信息(数字转汉字) */	private IPluginProfileVo getNumberUpperFunc() {		IFunctionBuilder bf = RegVds.getPlugin().getFunctiontPlugin();		IPluginProfileVo p1 = bf.setCode(NumberUpperFunc.D_Code)				.setName("数字转汉字-name")				.setDesc("数字转汉字")				.setAuthor("徐刚")				.addInputParam(bf.newInput().setType(VariableType.Integer).setDesc("数字").build())  //参数不能修改				.setEntry(NumberUpperFunc.class)				.setOutput(bf.newOutput().setType(VariableType.Char).setDesc("汉字大写").build()) //参数不能修改				.setExample("使用示例说明，\r\n换号,这是示例说明")//只能修改描述的信息				.build();		return p1;	}	/** 添加规则元信息(多值返回 数字转汉字) */	private IPluginProfileVo getNumberUpperRolueValue() {		IRuleBuilder br = RegVds.getPlugin().getRulePlugin();		IEditorBuilder editBuild = RegVds.getBuilder().getEditorBuilder();		editBuild.setType(EditorType.Select)				.addOption(editBuild.newOption().setLabel("男").setValue(NumberUpperRolue.Sex.male.name()).build())				.addOption(editBuild.newOption().setLabel("女").setValue(NumberUpperRolue.Sex.female.name()).build());		IPluginProfileVo p2 = br.setCode(NumberUpperRolue.D_Code).setName("把指定数字值转汉字").setAuthor("徐刚")				.setDesc("把指定数字值转汉字desc")				.addInput(br.newInput().setCode(NumberUpperRolue.INPUT_NAME).setName("性名").setType(VariableType.Char)						.build())				.addInput(br.newInput().setCode(NumberUpperRolue.INPUT_AGE).setName("年龄").setType(VariableType.Integer)						.build())				.addInput(br.newInput().setCode(NumberUpperRolue.INPUT_SEX).setName("性别").setType(VariableType.Char)						.setEditor(editBuild.build()).build())				.addOutput(br.newOutput().setCode(NumberUpperRolue.OUT_AGE).setName("年龄").setType(VariableType.Integer)						.build())				.addOutput(br.newOutput().setCode(NumberUpperRolue.OUT_NAME).setName("性名").setType(VariableType.Char)						.build())				.addOutput(br.newOutput().setCode(NumberUpperRolue.OUT_RETIRE).setName("退休")						.setType(VariableType.Boolean).build()) // 是否允许退休				.addOutput(br.newOutput().setCode(NumberUpperRolue.OUT_CHINESE).setName("汉字大写")						.setType(VariableType.Char).build())				.setPagePath("pages")				.setEntry(NumberUpperRolue.class).build();		return p2;	}	/** 添加规则元信息(把指定列 数字转汉字) */	private IPluginProfileVo getNumberUpperRolueEntity() {		IRuleBuilder br = RegVds.getPlugin().getRulePlugin();		// 添加规则元信息(把指定列 数字转汉字)		IRuleBuilder br2 = RegVds.getPlugin().getRulePlugin();		IEntityBuilder entryBuild = RegVds.getBuilder().getEntityProfileBuilder();		IRuleBuilder.IRuleInputBuilder ruleInputEntry = br.newInput().setCode(NumberUpperRolueEntity.INPUT_USERLLIST)				.setName("用户列表").setType(VariableType.Entity)				.addField(entryBuild.newField().setCode("age").setName("年龄").setType(VariableType.Integer).build())				.addField(entryBuild.newField().setCode("name").setName("性名").setType(VariableType.Char).build())				.addField(entryBuild.newField().setCode(NumberUpperRolueEntity.FD_CHINESE).setName("汉字大写")						.setType(VariableType.Char).build());		IRuleBuilder.IRuleOutputBuilder ruleOutputEntry = br.newOutput().setCode(NumberUpperRolueEntity.OUT_USERLLIST)				.setName("用户列表实体").setType(VariableType.Entity)				.addField(entryBuild.newField().setCode(NumberUpperRolueEntity.FD_AGE).setName("年龄")						.setType(VariableType.Integer).build())				.addField(entryBuild.newField().setCode(NumberUpperRolueEntity.FD_NAME).setName("性名")						.setType(VariableType.Char).build())				.addField(entryBuild.newField().setCode(NumberUpperRolueEntity.FD_CHINESE).setName("汉字大写")						.setType(VariableType.Char).build());		IPluginProfileVo p4 = br2.setCode(NumberUpperRolueEntity.D_Code).setName("把指定列 数字转汉字-entity")				.setDesc("把指定列 数字转汉字-实体").setAuthor("徐刚").addInput(ruleInputEntry.build())				.addInput(br.newInput().setCode(NumberUpperRolueEntity.INPUT_AGE).setName("转换列名")						.setType(VariableType.Char).build())				.addOutput(ruleOutputEntry.build()).addOutput(br.newOutput().setCode(NumberUpperRolueEntity.OUT_COUNT)						.setName("转换个数").setType(VariableType.Integer).build())				.setEntry(NumberUpperRolueEntity.class).build();		return p4;	}	private IPluginProfileVo getHttpCommand() {		IHttpCommandBuilder cb = RegVds.getPlugin().getHttpCommandPlugin();		IPluginProfileVo p3 = cb.setCode("mycmd")				.setName("我的扩展处理")				.setAuthor("徐刚")				.setEntry(ExampleMyHttpCommand.class)				.build();		return p3;	}		private IPluginProfileVo getHttpIndexCommand() {		IHttpCommandBuilder cb = RegVds.getPlugin().getHttpCommandPlugin();		IPluginProfileVo p3 = cb.setCode("index")				.setName("例子首页")				.setAuthor("纪其俊")				.setEntry(PluginIndexCommand.class)				.setPagePath("pages")				.build();		return p3;	}	private IPluginProfileVo getTimerTaskCommand() {		IHttpCommandBuilder cb = RegVds.getPlugin().getHttpCommandPlugin();		IPluginProfileVo p3 = cb.setCode("timerTask")				.setName("例子定时器")				.setAuthor("纪其俊")				.setEntry(TimerTaskCommand.class)				.setPagePath("pages")				.build();		return p3;	}		/** 银弹谷-更新实体 */	private IPluginProfileVo getUpdateDataView() {		IRuleBuilder br = RegVds.getPlugin().getRulePlugin();		IEntityBuilder entryBuild = RegVds.getBuilder().getEntityProfileBuilder();		IRuleBuilder.IRuleInputBuilder ruleInputEntry = br.newInput().setCode(UpdateRecord.D_InputEntity)				.setName("更新实体").setType(VariableType.Entity)				.addField(entryBuild.newField().setCode("id").setType(VariableType.Char).build());		br.setCode(UpdateRecord.D_CODE).setName("银弹谷-更新实体").setDesc("代替toone-UpdateRecord规则").setAuthor("jiqj")				.addInput(ruleInputEntry.build())				.addInput(br.newInput().setType(VariableType.Boolean).setCode(UpdateRecord.D_SaveAll)						.setName("insertOrUpdate").build())				.addInput(br.newInput().setType(VariableType.Char).setCode(UpdateRecord.D_TableName).setName("表明")						.build())				.addOutput(br.newOutput().setType(VariableType.Integer).setCode(UpdateRecord.D_OutUpdateCount)						.setName("更新记录数").build())				.setPagePath("pages")				.setEntry(UpdateRecord.class);		return br.build();	}	/** 银弹谷-实体删除记录 */	private IPluginProfileVo getDeleteRecordById() {		IRuleBuilder br = RegVds.getPlugin().getRulePlugin();		IRuleBuilder.IRuleInputBuilder ruleInputEntry = br.newInput().setCode(DeleteRecordById.D_PARAM_ID)				.setName("实体记录id").setType(VariableType.Char);		br.setCode(DeleteRecordById.D_CODE).setName("银弹谷-删除记录").setDesc("代替toone-DeleteRecordById").setAuthor("jiqj")				.addInput(ruleInputEntry.build())				.addInput(br.newInput().setType(VariableType.Char).setCode(DeleteRecordById.D_TableName).setName("表明")						.build())				.addOutput(br.newOutput().setType(VariableType.Integer).setCode(DeleteRecordById.D_OutUpdateCount)						.setName("删除记录数").build())				.setEntry(DeleteRecordById.class);		return br.build();	} 	private IPluginProfileVo getSystemStartup() {		ISystemStartupBuilder sb = RegVds.getPlugin().getSystemStartupPlugin();				ISystemStartupProfileVo vo = sb.setName("我的执行系统启动事")				.setCode(SystemStartupExample.PluginCode) //插件编码不能修改				.setDesc("我的执行系统启动事") //只能修改描述的信息				.setConfigPath("./config.xml")				.setAuthor("徐刚")				.setEntry(SystemStartupExample.class)				.build();		return vo;	}	private IPluginProfileVo getSystemStartupDemo() {		ISystemStartupBuilder sb = RegVds.getPlugin().getSystemStartupPlugin();				ISystemStartupProfileVo vo = sb.setName("我的执行系统启动事2")				.setCode(SystemStartupDemo.PluginCode) //插件编码不能修改				.setDesc("我的执行系统启动事2") //只能修改描述的信息				.setConfigPath("./config.xml")				.setAuthor("徐刚")				.setEntry(SystemStartupDemo.class)				.build();		return vo;	}}