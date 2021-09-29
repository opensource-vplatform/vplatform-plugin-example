isc.ClassFactory.defineClass("JGDevLabel", "DynamicForm");
isc.JGDevLabel.addProperties({
	Alias: "文本",
	MultiHeight: 30,
	MultiWidth: 235,
	Left : 50,
	datasource:null,
	fieldCode:null,
	TitleWidth:76,
	Top : 50
});
isc.JGDevLabel.addMethods({
	init: function () {
		this.titleWidth = this.TitleWidth;
		this.left = this.Left;
		this.top = this.Top;
		this.width = this.MultiWidth;
		this.height = this.MultiHeight;
		this.enabled = !this.Disabled; 
		this.items = [{
			width:"*",
			title : this.Alias,
			type : "text",
			name : this.fieldCode
		}];
		return this.Super("init", arguments);
	}
});