var parentTile = [];
var initTile = 'url(../output/mosaic/0/0/0.png)';

window.onload=function(){
    reset();
};

function zoom_in(element){
	
	var img_url = document.getElementById("image").style.backgroundImage;
	var regExp = /\/\d+\/\d+\/\d+.png/;
	var end_str = img_url.match(regExp)[0];
	var positions_str = end_str.slice(1,end_str.length-4);
	var tab_positions = positions_str.split('/');
	
	parentTile.push(img_url);
	
	var isRight = 0;
	var isBottom = 0;
	
	if(element.id=="TR"){
		isRight = 1;
	}
	if(element.id=='BL'){
		isBottom = 1;
	}
	if(element.id=='BR'){
		isRight = 1;
		isBottom = 1;
	}
		
	var parentZ = parseInt(tab_positions[0]*1+1);
	var parentX = parseInt(tab_positions[1]*2+isBottom);
	var parentY = parseInt(tab_positions[2]*2+isRight);
	
	document.getElementById("image").style.backgroundImage = 'url(../output/mosaic/'+parentZ+'/'+parentX+'/'+parentY+'.png)';
}

function zoom_out(){
	document.getElementById("image").style.backgroundImage = parentTile.pop();
}

function reset(){
	document.getElementById("image").style.backgroundImage = initTile;
}