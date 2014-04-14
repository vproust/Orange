var parentTile = [];
var initTile = 'url(../output/mosaic/0/0/0.png)';

window.onload=function(){
    reset();
};

function zoom_in(element){
    
    var tile = {};
    var img_url = $("#image").css('backgroundImage');
    var regExp = /\/\d+\/\d+\/\d+.png/;
    var end_str = img_url.match(regExp)[0];
    var begining_str = img_url.slice(4,img_url.length - end_str.length-1);
    var positions_str = end_str.slice(1,end_str.length-4);
    var tab_positions = positions_str.split('/');
    
    tile.img_url = img_url;
    tile.z = tab_positions[0];
    tile.x = tab_positions[1];
    tile.y = tab_positions[2];
    
    parentTile.push(tile);
    
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
    
    var tileZ = parseInt(tab_positions[0]*1+1);
    var tileX = parseInt(tab_positions[1]*2+isBottom);
    var tileY = parseInt(tab_positions[2]*2+isRight);
    
    $("#image").css('backgroundImage','url(../output/mosaic/'+tileZ+'/'+tileX+'/'+tileY+'.png)');
    
    var sisterTileZ = tileZ+1;
    var sisterTileX = tileX*2;
    var sisterTileY = tileY*2;
    
    set_navigator(tileZ, tileX, tileY);
    
    var sisterUrl = begining_str+'/'+sisterTileZ+'/'+sisterTileX+'/'+sisterTileY+'.png';
}

function zoom_out(){
    var tile = parentTile.pop();
    $("#image").css('backgroundImage', tile.img_url);
    set_navigator(tile.z, tile.x, tile.y);
    $('.image_navigator').show();
}

function reset(){
    $("#image").css('backgroundImage', initTile);
    set_navigator(0,0,0);
}

function set_navigator(tileZ, tileX, tileY){
    
    var navigator = $("#navigator");
    var navigator_window = $("#navigator_window");
    
    navigator.height(navigator_window.height()/Math.pow(2,tileZ));
    navigator.width(navigator_window.width()/Math.pow(2,tileZ));
    
    navigator.css('margin-top', navigator.height()*tileX+'px');
    navigator.css('margin-left', navigator.width()*tileY+'px');
    
    $('#info_row').html(tileX);
    $('#info_column').html(tileY);
    $('#info_zoom').html(tileZ);
}