var Application={
	initApplication : function(){
		$(document).load('pageinit','#page-one',function(){
			Application.initShowMhs();
		})
	},
	initShowMhs : function() {
		$.ajax({
			url :'https://api.myjson.com/bins/1czkzy',
			type:'get',
			beforeSend : function(){
				$.mobile.loading('show',{
					text : "please wait..." , 
					textVisible : true 
				});
			},
			success : function(dataObject){
				for (var i = 0; i <dataObject.length; i++) {
					var appendList= '<li class="ui-btn ui-corner-all"><a target="_self" id="detail-item" data-nama="'+dataObject[i].nama+'"><h2>'+dataObject[i].nama+
					'</h2><p id="jumlahitem">'+dataObject[i].jumlah+'</p><p><b>'+dataObject[i].jenis+'</b></p></a></li>';
					$('#list-mhs').append(appendList);
					$('#list-mhs').listview('refresh');
				};
			},
			complete : function(){
				$.mobile.loading('hide');
			}
		});
	}
};