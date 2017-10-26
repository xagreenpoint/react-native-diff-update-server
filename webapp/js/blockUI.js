var myBlock = {
		block : function(position){
			$(position).block({
				message: '<img src="' + ctx +'/images/loadingSubmit.gif" />正在处理,请稍后......',
				showOverlay: true,
				fadeIn: 600, 
				css: { 
		            width: '200px',
		            height: '20px',
		            padding: '0px', 
		            'line-height':'20px',
		            'font-size':'17px',
		            textAlign: 'center',
		            border:'1px solid #4682B4',
		            backgroundColor: '#FFFFFF', 
		            opacity: .7, 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px', 
		            color: '#4682B4',
		            'z-index':999
		        },
		        overlayCSS:  { 
		            backgroundColor:'#FFFFFF', 
		            opacity: .5
		        }
			});
		},
		unblock : function(position){
			$(position).unblock();
		}
		
}