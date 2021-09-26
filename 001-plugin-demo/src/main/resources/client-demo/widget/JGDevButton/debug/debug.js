vds.config({
    debug: true
});
vds.import("vds.mock.*");
vds.ready(function () {
    vds.mock.init("../../../../../manifest.json").then(function (mock) {
        mock.get("JGDevButton").then(function (widgetMock) {
            widgetMock.exec(function(properties){
                var widget = isc.JGDevButton.create(properties);
                widget.show();
            });
        }).catch(function (error) {
            console.error(error.message);
            throw error;
        });;
    }).catch(function (error) {
        console.error(error.message);
        throw error;
    });;
});