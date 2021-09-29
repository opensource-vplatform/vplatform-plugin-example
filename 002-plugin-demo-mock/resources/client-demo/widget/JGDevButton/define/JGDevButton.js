isc.ClassFactory.defineClass("JGDevButton", "Button");
isc.JGDevButton.addProperties({
	Alias: "按钮",
	MultiHeight: 70,
	MultiWidth: 50,
	Left : 50,
	Top : 50,
	OnClick: null,
	Disabled: false,
	click: function () {
		if (this.v3WidgetSelected) {
			return this.v3WidgetSelected(this);
		}
		this.Onclick();
	}
});
isc.JGDevButton.addMethods({
	init: function () {
		this.width = this.MultiWidth;
		this.height = this.MultiHeight;
		this.enabled = !this.Disabled;
		this.title = this.Alias;
		this.left = this.Left;
		this.top = this.Top;
		this.click = this.OnClick;
		return this.Super("init", arguments);
	},
	setDisabled : function(value){
		this.setEnabled(!value);
	}
});