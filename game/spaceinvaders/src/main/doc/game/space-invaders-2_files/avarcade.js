/*
AV Arcade front-end javascript functions

Author: Andy Venus
Rating stars rollover by Addam M. Driver

*/

var sMax;	// Isthe maximum number of stars
var holder; // Is the holding pattern for clicked state
var preSet; // Is the PreSet value onces a selection has been made
var rated;
var glWidth; // global game width
var glHeight; // global game height
var fullscreen_active; // Toggle fullscreen game active

window.onresize = resizeFullscreen;

// The page has loaded, lets get ready
$(document).ready(function() {
	// Preload images
	var images = [SITE_URL+TEMPLATE_URL+'/icons/notification_comment.png', SITE_URL+TEMPLATE_URL+'/icons/notification_highscore.png', SITE_URL+TEMPLATE_URL+'/icons/notification_play.png', SITE_URL+TEMPLATE_URL+'/icons/notification_rating.png', SITE_URL+TEMPLATE_URL+'/icons/notification_toofast.png', SITE_URL+TEMPLATE_URL+'/icons/notification_pm.png', SITE_URL+TEMPLATE_URL+'/icons/notification_friend.png', SITE_URL+TEMPLATE_URL+'/icons/notification_error.png', SITE_URL+TEMPLATE_URL+'/icons/notification_forum.png', SITE_URL+"/images/loader.gif"];
	preload(images);

	if ($("#ava-game_container").length) {
		// Click events for the view game page
		$('#favbutton').click(AddFav);
	}
	$('#notification_quit').click(hideNotification);
	
	$('#notification').mouseenter(function() {
		clearTimeout(notificationTO);
	});
	$('#notification').mouseleave(function() {
		notificationTO = setTimeout(hideNotification, 4000);
	});
	notificationTO = '';
	
	if (NEW_PMS == 1) {
		if (TOTAL_PMS == 1) {
			message = N_ONE_NEW_PM;
		}
		else {
			message = N_MULTIPLE_NEW_PMS1+' '+TOTAL_PMS+' '+N_MULTIPLE_NEW_PMS2;
		}
		if (SEO_ON == 0) {
			messages_link = '?task=messages';
		}
		else {
			messages_link = 'messages';
		}
		displayNotification(message+' <a href="'+SITE_URL+'/'+messages_link+'">'+N_VIEW+'</a>', 0, 'pm')
	}
	if (NEW_FRS == 1) {
		if (TOTAL_FRS == 1) {
			message = N_ONE_NEW_FR;
		}
		else {
			message = N_MULTIPLE_NEW_FRS1+' '+TOTAL_FRS+' '+N_MULTIPLE_NEW_FRS2;
		}
		if (SEO_ON == 0) {
			friends_link = '?task=friends';
		}
		else {
			friends_link = 'friends';
		}
		displayNotification(message+' <a href="'+SITE_URL+'/'+friends_link+'">'+N_VIEW+'</a>', 0, 'friend');
	}
});

// AJAX for all browsers
function GetXmlHttpObject() {
    var xmlHttp = null;
    try {
        // Firefox, Opera 8.0+, Safari
        xmlHttp = new XMLHttpRequest();
    }
    catch(e) {
        //Internet Explorer
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch(e) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return xmlHttp;
}

// AJAX POST FUNCTION
function AjaxPost(url, param, success_function) {
	xmlHttp = GetXmlHttpObject();
    if (xmlHttp == null) {
        alert("Your browser doesn't support AJAX. You should upgrade it!")
        return
    }
    xmlHttp.onreadystatechange = success_function;
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlHttp.send(param);
}

function div(d) {
    return document.getElementById(d);
}

// Rollover for image Stars //
function rating(num){
	sMax = 0;	// Isthe maximum number of stars
	for(n=0; n<num.parentNode.childNodes.length; n++){
		if(num.parentNode.childNodes[n].nodeName == "A"){
			sMax++;	
		}
	}
	
	if(!rated){
		s = num.id.replace("_", ''); // Get the selected star
		a = 0;
		for(i=1; i<=sMax; i++){		
			if(i<=s){
				document.getElementById("_"+i).className = "on";
				holder = a+1;
				a++;
			}else{
				document.getElementById("_"+i).className = "";
			}
		}
	}
}

// For when you roll out of the the whole thing //
function off(me){
	if(!rated){
		if(!preSet){	
			for(i=1; i<=sMax; i++){		
				document.getElementById("_"+i).className = "";
			}
		}else{
			rating(preSet);
		}
	}
}

// When you actually rate something //
function rateIt(me, id){
	if(!rated){
		preSet = me;
		rated=1;
		sendRate(me, id);
		rating(me);
	}
}

// Send the rating information somewhere using Ajax or something like that
function sendRate(sel, id){
	$.ajax({
		url: SITE_URL+'/includes/view_game/ajax/add_rating.php',
		type: 'POST',
		data: "id=" + id + "&rating=" + sel.title,
		success: function(data) {
			error = 0;
			try {
				result = eval(data);
			}
			catch(err) {
				error = 1;
			}
			if (error == 0) {
				if (result['success'] == 0) {
					displayNotification(result['message'], 10000, 'toofast');
				}
				else {
					updatePoints(result['points'], result['message'], 'rating');
				}
			}
		}
	});
}

// ADD/DELETE FAV GAME
function AddFav() {
	$.ajax({
		url: SITE_URL+'/includes/view_game/ajax/add_fav.php',
		type: 'POST',
		data: "id=" + ID
	});
	if ($('#favbutton a').html() == GAME_UNFAVOURITE) {
		$('#favbutton a').html(GAME_FAVOURITE);
	}
	else {
		$('#favbutton a').html(GAME_UNFAVOURITE);
	}
}


// ADD COMMENT

function AddComment(id, site_url, type) {
	if ($('#the_comment').val() != '') {
	$('#comment_submit').attr("disabled", "disabled");
	$('#comment_submit').val("Adding comment...");
	
	theComment = encodeURIComponent($('#the_comment').val()); 
	
	if (type == 'game') {
		post_url = "/includes/view_game/ajax/add_comment.php"
	}
	else {
		post_url = "/includes/news/ajax/news_add_comment.php"
	}
	
	$.ajax({
		url: SITE_URL+post_url,
		type: 'POST',
		data: "comment="+theComment+"&id="+ID,
		success: function(data) {
			if (data == '') {
	 			alert("An error occured in sending your comment");
 			}
 			else if (data == '<e1>') {
	 			displayNotification("Please wait 60 seconds between comments", 8000, 'toofast');
	 			$('#comment_submit').val("Add comment");
				$('#comment_submit').removeAttr("disabled");
 			}
 			else {
 				if (type == 'game') {
  					var container = document.getElementById('comment_list');
  				}
  				else {
  					var container = document.getElementById('news_comment_list');
  				}
  				var new_element = document.createElement('li');
  				new_element.innerHTML = data;
  				container.insertBefore(new_element, container.firstChild);
  				window.location.hash="1"; 
  				$('#comment_submit').val("Comment added!");
  				$('#the_comment').val('');
  				
  				updatePoints(COMMENT_POINTS, N_POINTS_EARNED1+' <span style=\"font-weight:bold;\">'+COMMENT_POINTS+' '+N_POINTS_EARNED2+'</span> '+N_POINTS_EARNED_COMMENT, 'comment');
  				
  				setTimeout("EnableButton()",30000);
  			}
		}
	});
	}
}

// Re-enable add-comment button

function EnableButton () {
	$('#comment_submit').val("Add comment");
	$('#comment_submit').removeAttr("disabled");
}

function clickclear(thisfield, defaulttext) {
	if (thisfield.value == defaulttext) {
		thisfield.value = "";
	}
}
    
function clickrecall(thisfield, defaulttext) {
	if (thisfield.value == "") {
		thisfield.value = defaulttext;
	}
}

// Ajax delete comment
function DeleteComment(id, type) {
	if (!type) {
		url = "/admin/includes/delete_comment.php";
	}
	else {
		url = "/admin/includes/delete_news_comment.php";
	}
	$.ajax({
		url: SITE_URL+url,
		type: 'POST',
		data: "id=" + id,
		success: function(data) {
			$('#comment-' + id).toggle();
		}
	});
}

// Load game after ad has been shown
function ShowGame() {
	$('#ava-game_container').css('display', 'inherit');
	$('#ava-advert_container').css('display', 'none');
}

// Show ad countdown
function countdown(){
	if (AD_COUNTDOWN!=1){
		AD_COUNTDOWN-=1
		$('#zzz').html(AD_COUNTDOWN);
	}
	else{
		ShowGame();
		return
	}
	setTimeout("countdown()",1000)
} 

// Highscore pagintation
function HighscorePage(id, page, leaderboard, site_url, type) {
	$('#highscores_ajax').css('opacity', 0.5);
	$('#highscore_pages').html('<img src="'+SITE_URL+'/images/loader.gif" />');
	
	if (leaderboard != 'unspecified') {
		lb = div('leaderboard_select').value;
		scope = div('leaderboard_scope').value;
	}
	else {
		lb = leaderboard;
		scope = 'all';
	}
	
	$.ajax({
		url: SITE_URL+"/includes/view_game/highscores.inc.php",
		data: "id="+id+"&page="+page+'&lb_id='+lb+'&scope='+scope,
		success: function(data) {
			$('#highscores_ajax').html(data);
			$('#highscores_ajax').css('opacity', 1);
		}
	});
}

// Delete highscore
function DeleteHighscore(id, site_url) {
	$.ajax({
		url: SITE_URL+"/admin/includes/delete_highscore.php",
		type: 'POST',
		data: "id=" + id,
		success: function(data) {
			$('#game_highscore'+id).toggle();
		}
	});
}

// REPORT GAME/COMMENT
function SendReport(id, site_url, type) {
	$('#report_submit').attr("disabled", "disabled");
	
	theReport = $('#the_report').val();
	
	$.ajax({
		url: SITE_URL+"/includes/view_game/ajax/add_report.php",
		type: 'POST',
		data: "report="+theReport+"&id="+id+"&type="+type,
		success: function(data) {
			$('#report_submit').removeAttr("disabled");
			HidePopup();
		}
	});
}

// Window popup
function ShowJsPopup(id, url, title) {
	window.open (url, "mywindow","menubar=1,resizable=1,width=620,height=250"); 
}

// Show AV Arcade JS popup
function ShowPopup(id, url, title) {
	$('#overlay').height($(document).height());
	$('#overlay').css('display', 'inherit');
	FadeDiv('overlay', 0, 'up', 0.5);
	
	$('#ava-game_container').css('visibility', 'hidden');
	$('#' + id + '-title').html(title);
	$('#'+id).css('display', 'inherit');
	$('#overlay').unbind('click');
	$('#overlay').click(HidePopup);
	
	$('#'+id + '-content').html('<img src="'+SITE_URL+'/images/loader.gif" />');
	
	$.ajax({
		url: url,
		success: function(data) {
			$('#'+id + '-content').html(data);
		}
	});
}

// Hide AV Arcade JS popup
function HidePopup() {
	$('#ava-popup').css('display', 'none');
    $('#overlay').css('display', 'none');
    $('#ava-game_container').css('visibility', 'visible');
}

// Fade a div into view
function FadeDiv(id, opacity, fade, limit) {
    	if (opacity < limit) {
       	    opacity = opacity + 0.08
        	div(id).style.opacity = opacity;
        	setTimeout('FadeDiv("' + id + '", ' + opacity + ', "up", ' + limit + ')', 10); // call doMove() in 20 msec
    	}
}

function ResizeFlash(gHeight, gWidth) {
	wHeight = $(window).height();
	
	glHeight = gHeight;
	glWidth = gWidth;
	
	w1 = (wHeight / gHeight);
	w2 = (gWidth * w1);
	
	halfWidth = (w2 / 2);
	
	if ($.browser.msie) {
		$('#eID').attr("width", w2);
		$('#eID').attr("height",  wHeight);
	}
	else {
		$('#eID').attr("width", w2);
		$('#eID').attr("height",  wHeight);
	}
	$('#ava-game_container').attr('class', 'flash_popup');
	$('#ava-game_container').css('marginLeft', '-'+halfWidth+'px');
	
	$('#overlay').css('display', 'inline');
	page_height = $(document).height();
	$('#overlay').height(page_height);
	FadeDiv('overlay', 0, 'up', 0.5);
	$('#overlay').unbind('click');
	$('#close_fs').css('display', 'inherit');
	
	fullscreen_active = 1;
}
function ResetFlash() {
	if ($.browser.msie) {
		$('#eID').attr("width", glWidth);
		$('#eID').attr("height",  glHeight);
	}
	else {
		$('#eID').attr("width", glWidth);
		$('#eID').attr("height",  glHeight);
	}
	$('#ava-game_container').attr('class', 'flash_nopopup');
	$('#ava-game_container').css('marginLeft', '0px');
	
	$('#overlay').toggle();
	$('#close_fs').toggle();
	
	fullscreen_active = 0;
}
function resizeFullscreen() {
	if (fullscreen_active == 1) {
		ResizeFlash(glHeight, glWidth);
	}
}

function searchSubmit(site_url, extension) {
	value = $('#search_textbox').val();
	value = value.replace(' ', '+');
	window.location = site_url+'/search/'+value+extension;
}

// 5.5 Friends //

// Delete friend
function ManageFriend(id, type, location) {
	if (type == 'delete_friend') {
		do_delete = confirm(DELETE_FRIEND_CONFIRM);
	}
	else {
		do_delete = "TRUE";
	}

	if (do_delete) { 
	
		$.ajax({
			url: SITE_URL+"/includes/ajax/manage_friends.php",
			type: 'POST',
			data: "id=" + id + "&type=" + type,
			success: function(data) {
				$('#report_submit').removeAttr("disabled");
				HidePopup();
			}
		});
	
		if (type == 'accept_request') {
			 $('#friend' + id).css('borderColor', '#2c6b2f');
			 $('#friend_buttons' + id).html('<a href="index.php?task=send_message&id='+id+'"><img src="images/friend_message.png" /></a> <a href="#" onclick="ManageFriend('+id+', \'delete_friend\', \'friends_page\');return false"><img src="images/delete_friend.png" /></a>');
		}
		else if (type == 'send_request') {
			 $('#friend_button').html('<a href="#">'+REQUEST_SENT+'</a>');
		}
		else {
			if (location == 'friends_page') {
				$('#friend' + id).toggle();
			}
			else {
				$('#friend_button').html('<a href="#">'+UNFRIENDED+'</a>');
			}
		}
	}
}

// 5.5 Track outbound click
function LinkOut(id) {
	$.ajax({
		url: SITE_URL+"/includes/ajax/link_out.php",
		type: 'POST',
		data: "id=" + id
	});
}

// Submit challenge request
function SubmitChallenge(game_id) {
	friend_id = $('#challenge_friend_id').val();
	leaderboard = $('#challenge_score_type').val();
	
	$.ajax({
		url: SITE_URL+"/includes/ajax/challenge_friend_submit.php",
		type: 'POST',
		data: "game_id=" + game_id + "&friend_id=" + friend_id + "&leaderboard=" + leaderboard,
		success: function(data) {
			error = 0;
			try {
				result = eval(data);
			}
			catch(err) {
				error = 1;
			}
			if (error == 0) {
				if (result['success'] == 0) {
					displayNotification(result['message'], 10000, 'error');
				}
				else {
					updatePoints(result['points'], result['message'], 'highscore');
					HidePopup();
					$('#game_message').html(CHALLENGE_SUBMITTED+' - <a href="#" id="challenge_link" onclick="ShowPopup(\'ava-popup\', \''+SITE_URL+'/includes/view_game/ajax/challenge_friend.php?id='+game_id+'\')">'+CHALLENGE_ANOTHER+'</a>');
				}
			}
		}
	});
}

// Comments pagintation
function CommentPage(id, page) {
	$('#comment_pages').html('<img src="'+SITE_URL+'/images/loader.gif" />');
	
	$.ajax({
		url: SITE_URL+"/includes/view_game/ajax/game_comments.php",
		type: 'POST',
		data: "id="+id+"&page="+page,
		success: function(data) {
			$('#comments').html(data);
		}
	});	
}

// Update points
function updatePoints(points, message, type, duration) {
	if (points != 0) {
		if (!type) {
			type = 'error';
		}
		if (!duration) {
			duration = 6000;
		}
		curpoints = parseInt($('.ua_points').html());
		newpoints = curpoints + points;
		curpoints = $('.ua_points').html(newpoints);
	
		displayNotification(message,duration,type);
	}
}

// Notifications
function displayNotification(message, duration, type) {
	if (!type) {
		type = 'error';
	}
	if (!duration) {
		duration = 0;
	}
	clearTimeout(notificationTO);
	$('.notification').css('display', 'inherit');
	$('.notification').css('marginTop', '30px');
	$('.notification_message').html(message);
	$('.notification_icon').html('<img src="'+SITE_URL+TEMPLATE_URL+'/icons/notification_'+type+'.png" />');
	$('.notification').animate({
		opacity: 1.00,
		marginTop: '10px'
	}, 500, function() {
		if (duration != 0) {
			notificationTO = setTimeout(hideNotification, duration);
		}
	});
	
}
function hideNotification() {
	clearTimeout(notificationTO);
	$('.notification').animate({
		opacity: 0.00,
	}, 500, function() {
		$('.notification').css('opacity', '0.00');
		$('.notification').css('display', 'none');
	});
}

// Update game plays
function GameAddPlay() {
	$.ajax({
		url: SITE_URL+"/includes/view_game/ajax/game_play.php",
		type: 'POST',
		data: "game_id=" + ID
	});
}

// Update user game play points
function UserAddPlay() {
	$.ajax({
		url: SITE_URL+"/includes/view_game/ajax/game_points.php",
		type: 'POST',
		data: 'core=1&game_id=' + ID,
		success: function(data) {
			error = 0;
			try {
				result = eval(data);
			}
			catch(err) {
				error = 1;
			}
			if (error == 0) {
				if (result['success'] == 0) {
					displayNotification(result['message'], 10000, 'toofast');
				}
				else {
					updatePoints(result['points'], result['message'], 'play');
				}
			}
		}
	});
}

// Preload required images
function preload(sources)
{
  var images = [];
  for (i = 0, alength = sources.length; i < alength; ++i) {
    images[i] = new Image();
    images[i].src = sources[i];
  }
}