isc.ClassFactory.defineClass("JGDevTextBox", "DynamicForm");
isc.JGDevTextBox.addProperties({
	Alias: "文本",
	MultiHeight: 30,
	MultiWidth: 235,
	Left : 50,
	datasource:null,
	fieldCode:null,
	TitleWidth:76,
	Top : 50,
	OnLabelClick: null,
	OnKeyDown: null,
	OnLeave: null
});
isc.JGDevTextBox.addMethods({
	init: function () {
		this.titleWidth = this.TitleWidth;
		this.left = this.Left;
		this.top = this.Top;
		this.width = this.MultiWidth;
		this.height = this.MultiHeight;
		this.enabled = !this.Disabled;
		this.valuesManager = isc.ValuesManager.getByDatasource(this.datasource);
		this.items = [{
			width:"*",
			title : this.Alias,
			type : "text",
			name : this.fieldCode,
			titleClick : this.OnLabelClick,
			//keyDown : this.OnKeyDown,
			blur : this.handleBlur
		}];
		return this.Super("init", arguments);
	},
	handleBlur : function(){
		this.form.valuesManager.saveData();
		if(this.OnLeave){
			this.OnLeave();
		}
	}
});