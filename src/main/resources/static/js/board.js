// SNS post
(function($) {
	"use strict";
	
	$.fn.snspost = function(opts) {
		var loc = '';
		opts = $.extend({}, {type:'twitter', event:'click', content:''}, opts);
		opts.content = encodeURIComponent(opts.content);
		switch(opts.type) {
			case 'facebook':
				loc = '//www.facebook.com/share.php?t='+opts.content+'&u='+encodeURIComponent(opts.url||location.href);
				break;
			case 'delicious':
				loc = '//www.delicious.com/save?v=5&noui&jump=close&url='+encodeURIComponent(opts.url||location.href)+'&title='+opts.content;
				break;
			case 'twitter':
				loc = '//twitter.com/share?url='+opts.content;
				break;
			case 'google' :
				loc = '//plus.google.com/share?url='+encodeURIComponent(opts.url||location.href)+'?l=ko='+opts.content;
				break;
		}
		this.bind(opts.event, function() {
			window.open(loc);
			return false;
		});
	};
	$.snspost = function(selectors, action) {
		$.each(selectors, function(key,val) {
			$(val).snspost( $.extend({}, action, {type:key}) );
		});
	};
		
	$(document).ready(function(){			
		// category open
		$(".category-dropdown").find('a[data-toggle=category-dropdown]').on('click', function(){
			$(this).toggleClass('open').next('ul').toggle().toggleClass('close open');			
		});
		
		var cate_text = $('.category-dropdown input[type=hidden]').val();		
		$('#category-text').text(cate_text);	
	});
})(jQuery);
