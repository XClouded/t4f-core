/*jslint evil:true */
/*global DISQUS:false */
/**
 * Dynamic thread loader
 *
 * 
 *  * 
 * 
*/

(function (window) {
    var DISQUS = window.DISQUS;
    var jsonData, cookieMessages, session;

    // 
    if (!DISQUS || typeof DISQUS === 'function') {
        throw "DISQUS object is not initialized";
    }
    // 
    // json_data and default_json django template variables will close
    // and re-open javascript comment tags

    /* */ jsonData = {"reactions": [], "reactions_limit": 10, "ordered_highlighted": [], "posts": {"591865208": {"edited": false, "author_is_moderator": false, "from_request_user": null, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "Very nice summary, thanks!\n\nOne sentence that I struggled with was this:\nNetty never calls the handler methods of the same\u00a0ChannelHandler\u00a0instance simultaneously if and only if the handler is not annotated with\u00a0@Sharable, regardless the type of handler methods - inbound, outbound, or life cycle event handler methods.\n\nCould that perhaps be written as:\nNetty will never call a ChannelHandler's methods concurrently, unless the ChannelHandler is annotated with @Shareable. This is regardless of the type of handler methods - inbound\u00a0outbound, or lifecycle event methods.Regards,/Daniel", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2012-07-19_06:58:50", "date": "7 months ago", "message": "<p>Very nice summary, thanks!</p>\n\n<p>One sentence that I struggled with was this:<br>Netty never calls the handler methods of the same\u00a0ChannelHandler\u00a0instance simultaneously if and only if the handler is not annotated with\u00a0@Sharable, regardless the type of handler methods - inbound, outbound, or life cycle event handler methods.</p>\n\n<p>Could that perhaps be written as:<br>Netty will never call a ChannelHandler's methods concurrently, unless the ChannelHandler is annotated with @Shareable. This is regardless of the type of handler methods - inbound\u00a0outbound, or lifecycle event methods.Regards,/Daniel</p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 1, "user_voted": null, "num_replies": 1, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 1, "user_key": "1be2cab21d939dc1abb2ed47b46877c6", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}, "776334616": {"edited": false, "author_is_moderator": false, "from_request_user": false, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "\"If multithreaded\u00a0EventExecutor\u00a0or\u00a0EventLoop\u00a0is specified, one of the threads will be chosen first and then the chosen thread will be used until deregistration.\" \u00a0Does this mean that we need to do something extra for getting multithreading in addition to passing EventLoopGroup in bootstrap class.?", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2013-01-24_00:31:34", "date": "3 weeks ago", "message": "<p>\"If multithreaded\u00a0EventExecutor\u00a0or\u00a0EventLoop\u00a0is specified, one of the threads will be chosen first and then the chosen thread will be used until deregistration.\" \u00a0Does this mean that we need to do something extra for getting multithreading in addition to passing EventLoopGroup in bootstrap class.?</p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 0, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 0, "user_key": "google-c4c458d132817abc7f621c553c64db41", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}, "635539562": {"edited": false, "author_is_moderator": false, "from_request_user": null, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "Problem: The ChannelInitializer.initChannel() method is called every time a client connects.\nThis means the client can only connect once. Every time after that will fail because you are adding the same handlers to the pipeline - which is not allowed (at least not for the EchoServerHandler).\n", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2012-08-31_10:20:36", "date": "5 months ago", "message": "<p>Problem: The ChannelInitializer.initChannel() method is called every time a client connects.<br>This means the client can only connect once. Every time after that will fail because you are adding the same handlers to the pipeline - which is not allowed (at least not for the EchoServerHandler).<br></p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 1, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 1, "user_key": "4a8da49e99c5bf58949242e6ab88bace", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}, "625605315": {"edited": false, "author_is_moderator": true, "from_request_user": false, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "\u00a0Hi Daniel - Thanks for your comment.\u00a0 I've just updated the page.\u00a0 By the way, you can edit this page by yourself. It's a Wiki. :-)\n\n", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2012-08-22_07:44:47", "date": "5 months ago", "message": "<p>\u00a0Hi Daniel - Thanks for your comment.\u00a0 I've just updated the page.\u00a0 By the way, you can edit this page by yourself. It's a Wiki. :-)</p>\n\n<p></p>", "approved": true, "is_last_child": true, "author_is_founder": true, "can_reply": true, "likes": 0, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": true, "has_been_anonymized": false, "highlighted": false, "parent_post_id": 591865208, "depth": 1, "points": 0, "user_key": "trustin", "author_is_creator": true, "email": "", "killed": false, "is_realtime": false}, "782252098": {"edited": false, "author_is_moderator": false, "from_request_user": false, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "Holy refactoring, Batman!\n\nDo you guys have any idea what your refactorings are doing to those of us trying to maintain a framework built on *top* of Netty? \u00a0My continuous build gets broken every few days - the latest gem being when somebody decided they didn't like \"get\" on getters and renamed everything method like getSomething() to something(). \u00a0I don't necessarily disagree about get being silly, but is there sometime that the refactoring madness will end?", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2013-01-29_13:51:59", "date": "2 weeks ago", "message": "<p>Holy refactoring, Batman!</p>\n\n<p>Do you guys have any idea what your refactorings are doing to those of us trying to maintain a framework built on *top* of Netty? \u00a0My continuous build gets broken every few days - the latest gem being when somebody decided they didn't like \"get\" on getters and renamed everything method like getSomething() to something(). \u00a0I don't necessarily disagree about get being silly, but is there sometime that the refactoring madness will end?</p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 0, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 0, "user_key": "facebook-571976629", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}}, "ordered_posts": [635539562, 591865208, 625605315, 782252098, 776334616], "realtime_enabled": false, "ready": true, "mediaembed": [], "has_more_reactions": false, "realtime_paused": true, "integration": {"receiver_url": null, "hide_user_votes": false, "reply_position": false, "disqus_logo": false}, "highlighted": {}, "reactions_start": 0, "media_url": "https://securecdn.disqus.com/1360872419", "users": {"1be2cab21d939dc1abb2ed47b46877c6": {"username": "Daniel Bevenius", "registered": false, "is_remote": false, "facebook": "", "verified": false, "about": "", "display_name": "Daniel Bevenius", "url": "http://disqus.com/guest/1be2cab21d939dc1abb2ed47b46877c6/", "remote_id": null, "blog": "", "points": 0, "avatar": "https://www.gravatar.com/avatar.php?default=https%3A%2F%2Fsecurecdn.disqus.com%2F1360872419%2Fimages%2Fnoavatar32.png&size=32&gravatar_id=1be2cab21d939dc1abb2ed47b46877c6", "remote_domain": "", "twitter": "", "remote_domain_name": ""}, "trustin": {"username": "trustin", "registered": true, "is_remote": false, "facebook": "", "verified": true, "about": "the best is yet to come", "display_name": "Trustin Lee", "url": "http://disqus.com/trustin/", "remote_id": null, "blog": "http://gleamynode.net/", "points": 0, "avatar": "https://securecdn.disqus.com/uploads/users/1892/1775/avatar32.jpg?1345635888", "remote_domain": "", "twitter": "", "remote_domain_name": ""}, "google-c4c458d132817abc7f621c553c64db41": {"username": "google-c4c458d132817abc7f621c553c64db41", "registered": true, "is_remote": true, "facebook": "", "verified": false, "about": "", "display_name": "Arun George", "url": "http://disqus.com/google-c4c458d132817abc7f621c553c64db41/", "remote_id": "c4c458d132817abc7f621c553c64db41", "blog": "", "points": 0, "avatar": "https://securecdn.disqus.com/1360872419/images/noavatar32.png", "remote_domain": 6, "twitter": "", "remote_domain_name": "Google"}, "4a8da49e99c5bf58949242e6ab88bace": {"username": "Anonymous", "registered": false, "is_remote": false, "facebook": "", "verified": false, "about": "", "display_name": "Anonymous", "url": "http://disqus.com/guest/4a8da49e99c5bf58949242e6ab88bace/", "remote_id": null, "blog": "", "points": 0, "avatar": "https://www.gravatar.com/avatar.php?default=https%3A%2F%2Fsecurecdn.disqus.com%2F1360872419%2Fimages%2Fnoavatar32.png&size=32&gravatar_id=4a8da49e99c5bf58949242e6ab88bace", "remote_domain": "", "twitter": "", "remote_domain_name": ""}, "facebook-571976629": {"username": "facebook-571976629", "registered": true, "is_remote": true, "facebook": "http://www.facebook.com/tim.boudreau", "verified": false, "about": "", "display_name": "Tim Boudreau", "url": "http://disqus.com/facebook-571976629/", "remote_id": "571976629", "blog": "http://www.facebook.com/tim.boudreau", "points": 4, "avatar": "https://securecdn.disqus.com/uploads/users/3620/8665/avatar32.jpg?1359485520", "remote_domain": 1, "twitter": "", "remote_domain_name": "Facebook"}}, "user_unapproved": {}, "messagesx": {"count": 0, "unread": []}, "thread": {"voters_count": 0, "offset_posts": 0, "slug": "netty_new_and_noteworthy_documentationnew_and_noteworthy", "likes": 12, "num_pages": 4, "days_alive": 0, "moderate_none": false, "voters": {}, "total_posts": 16, "realtime_paused": true, "queued": false, "pagination_type": "append", "user_vote": null, "num_posts": 5, "closed": false, "per_page": 5, "id": 749191546, "killed": false, "moderate_all": false}, "forum": {"use_media": false, "avatar_size": 32, "apiKey": "aaIiLdjeGA7XGyJq5fAE0zuaCC5EekgSrOaQ04wwPwjPKhW9UwWCpzmDzQec8Cju", "features": {}, "comment_max_words": 0, "mobile_theme_disabled": false, "is_early_adopter": false, "login_buttons_enabled": true, "streaming_realtime": false, "reply_position": false, "id": 1121660, "default_avatar_url": "https://securecdn.disqus.com/1360872419/images/noavatar32.png", "template": {"url": "https://securecdn.disqus.com/1360872419/uploads/themes/dsq7884a9652e94555c70f96b6be63be216.js?255", "mobile": {"url": "http://mediacdn.disqus.com/1360872419/uploads/themes/mobile/theme.js?254", "css": "http://mediacdn.disqus.com/1360872419/uploads/themes/mobile/theme.css?254"}, "api": "1.1", "name": "Houdini", "css": "https://securecdn.disqus.com/1360872419/uploads/themes/dsq7884a9652e94555c70f96b6be63be216.css?255"}, "max_depth": 0, "ranks_enabled": false, "lastUpdate": "", "linkbacks_enabled": false, "allow_anon_votes": true, "revert_new_login_flow": false, "stylesUrl": "https://securecdn.disqus.com/uploads/styles/112/1660/netty0.css", "show_avatar": true, "reactions_enabled": true, "disqus_auth_disabled": false, "name": "Netty project", "language": "en", "mentions_enabled": true, "url": "netty0", "allow_anon_post": true, "thread_votes_disabled": false, "hasCustomStyles": false, "moderate_all": false}, "settings": {"uploads_url": "http://media.disqus.com/uploads", "ssl_media_url": "https://securecdn.disqus.com/1360872419", "realtime_url": "http://rt.disqus.com/forums/realtime-cached.js", "facebook_app_id": "52254943976", "minify_js": true, "recaptcha_public_key": "6LdKMrwSAAAAAPPLVhQE9LPRW4LUSZb810_iaa8u", "read_only": false, "facebook_api_key": "52254943976", "juggler_url": "http://juggler.services.disqus.com", "debug": false, "disqus_url": "http://disqus.com", "media_url": "https://securecdn.disqus.com/1360872419"}, "ranks": {}, "request": {"sort": "hot", "is_authenticated": false, "user_type": "anon", "subscribe_on_post": 0, "missing_perm": null, "user_id": null, "remote_domain_name": "", "remote_domain": "", "is_verified": false, "profile_url": "", "username": "", "is_global_moderator": false, "sharing": {}, "timestamp": "2013-02-15_08:52:20", "is_moderator": false, "ordered_unapproved_posts": [], "unapproved_posts": {}, "forum": "netty0", "is_initial_load": true, "display_username": "", "points": null, "has_email": false, "moderator_can_edit": false, "is_remote": false, "userkey": "", "page": 1}, "context": {"use_twitter_signin": true, "use_fb_connect": false, "show_reply": true, "sigma_chance": 10, "use_google_signin": true, "switches": {"listactivity_replies": true, "juggler_enabled": true, "next_realtime_indicators": true, "community_icon": true, "static_styles": true, "stats": true, "website_addons": true, "firehose_gnip_http": true, "discovery_next": true, "show_captcha_on_links": true, "next_dragdrop_nag": true, "firehose_gnip": true, "firehose_pubsub": true, "dark_jester": true, "limit_get_posts_days_30d": true, "juggler_thread_onReady": true, "website_homepage_https_off": true, "disqus_trends": true, "discovery_next:top_placement": true, "upload_media": false, "shardpost:index": true, "filter_ads_by_country": true, "new_sort_paginator": true, "use_rs_paginator_5m": true, "firehose_push": true, "enable_link_affiliation": true, "limit_textdigger": true, "textdigger_crawler": true, "discovery_analytics": true, "discovery_next:truncate": true, "listactivity_replies_30d": true, "next_discard_low_rep": true, "mentions": true, "shardpost": true}, "forum_facebook_key": "", "use_yahoo": false, "subscribed": false, "active_gargoyle_switches": ["community_icon", "dark_jester", "discovery_analytics", "discovery_next", "discovery_next:top_placement", "discovery_next:truncate", "disqus_trends", "enable_link_affiliation", "filter_ads_by_country", "firehose_gnip", "firehose_gnip_http", "firehose_pubsub", "firehose_push", "juggler_enabled", "juggler_thread_onReady", "limit_get_posts_days_30d", "limit_textdigger", "listactivity_replies", "listactivity_replies_30d", "mentions", "new_sort_paginator", "next_discard_low_rep", "next_dragdrop_nag", "next_realtime_indicators", "shardpost", "shardpost:index", "show_captcha_on_links", "static_styles", "stats", "textdigger_crawler", "use_rs_paginator_5m", "website_addons", "website_homepage_https_off"], "realtime_speed": 15000, "use_openid": true}}; /* */
    /* __extrajson__ */
    cookieMessages = {"user_created": null, "post_has_profile": null, "post_twitter": null, "post_not_approved": null}; session = {"url": null, "name": null, "email": null};
    jsonData.forum.template = {"url": "https://securecdn.disqus.com/1360872419/uploads/themes/dsq7884a9652e94555c70f96b6be63be216.js?255", "mobile": {"url": "http://mediacdn.disqus.com/1360872419/uploads/themes/mobile/theme.js?254", "css": "http://mediacdn.disqus.com/1360872419/uploads/themes/mobile/theme.css?254"}, "api": "1.1", "name": "Houdini", "css": "https://securecdn.disqus.com/1360872419/uploads/themes/dsq7884a9652e94555c70f96b6be63be216.css?255"};
    jsonData.context.active_gargoyle_switches = ["community_icon", "dark_jester", "discovery_analytics", "discovery_next", "discovery_next:top_placement", "discovery_next:truncate", "disqus_trends", "enable_link_affiliation", "filter_ads_by_country", "firehose_gnip", "firehose_gnip_http", "firehose_pubsub", "firehose_push", "juggler_enabled", "juggler_thread_onReady", "limit_get_posts_days_30d", "limit_textdigger", "listactivity_replies", "listactivity_replies_30d", "mentions", "new_sort_paginator", "next_discard_low_rep", "next_dragdrop_nag", "next_realtime_indicators", "shardpost", "shardpost:index", "show_captcha_on_links", "static_styles", "stats", "textdigger_crawler", "use_rs_paginator_5m", "website_addons", "website_homepage_https_off"];
    jsonData.context.all_switches = {"listactivity_replies": true, "juggler_enabled": true, "next_realtime_indicators": true, "community_icon": true, "static_styles": true, "stats": true, "website_addons": true, "firehose_gnip_http": true, "discovery_next": true, "show_captcha_on_links": true, "next_dragdrop_nag": true, "firehose_gnip": true, "firehose_pubsub": true, "dark_jester": true, "limit_get_posts_days_30d": true, "juggler_thread_onReady": true, "website_homepage_https_off": true, "disqus_trends": true, "discovery_next:top_placement": true, "shardpost:index": true, "filter_ads_by_country": true, "new_sort_paginator": true, "use_rs_paginator_5m": true, "firehose_push": true, "enable_link_affiliation": true, "limit_textdigger": true, "textdigger_crawler": true, "discovery_analytics": true, "discovery_next:truncate": true, "listactivity_replies_30d": true, "next_discard_low_rep": true, "mentions": true, "shardpost": true};

    DISQUS.jsonData = jsonData;
    DISQUS.jsonData.cookie_messages = cookieMessages;
    DISQUS.jsonData.session = session;

    if (DISQUS.useSSL) {
        DISQUS.useSSL(DISQUS.jsonData.settings);
    }

    // The mappings below are for backwards compatibility--before we port all the code that
    // accesses jsonData.settings to DISQUS.settings

    var mappings = {
        debug:                'disqus.debug',
        minify_js:            'disqus.minified',
        read_only:            'disqus.readonly',
        recaptcha_public_key: 'disqus.recaptcha.key',
        facebook_app_id:      'disqus.facebook.appId',
        facebook_api_key:     'disqus.facebook.apiKey'
    };

    var urlMappings = {
        disqus_url:    'disqus.urls.main',
        media_url:     'disqus.urls.media',
        ssl_media_url: 'disqus.urls.sslMedia',
        realtime_url:  'disqus.urls.realtime',
        uploads_url:   'disqus.urls.uploads'
    };

    if (DISQUS.jsonData.context.switches.realtime_setting_change) {
        urlMappings.realtimeHost = 'realtime.host';
        urlMappings.realtimePort = 'realtime.port';
    }
    for (key in mappings) {
        if (mappings.hasOwnProperty(key)) {
            DISQUS.settings.set(mappings[key], DISQUS.jsonData.settings[key]);
        }
    }

    for (key in urlMappings) {
        if (urlMappings.hasOwnProperty(key)) {
            DISQUS.jsonData.settings[key] = DISQUS.settings.get(urlMappings[key]);
        }
    }

    DISQUS.jsonData.context.csrf_token = 'a368c21a5504d353cf805f9927914497';

    DISQUS.jsonData.urls = {
        login: 'http://disqus.com/profile/login/',
        logout: 'http://disqus.com/logout/',
        upload_remove: 'http://netty0.disqus.com/thread/netty_new_and_noteworthy_documentationnew_and_noteworthy/async_media_remove/',
        request_user_profile: 'http://disqus.com/AnonymousUser/',
        request_user_avatar: 'https://securecdn.disqus.com/1360872419/images/noavatar92.png',
        verify_email: 'http://disqus.com/verify/',
        remote_settings: 'http://netty0.disqus.com/_auth/embed/remote_settings/',
        edit_profile_window: 'http://disqus.com/embed/profile/edit/',
        embed_thread: 'https://netty0.disqus.com/thread.js',
        embed_vote: 'http://netty0.disqus.com/vote.js',
        embed_thread_vote: 'http://netty0.disqus.com/thread_vote.js',
        embed_thread_share: 'http://netty0.disqus.com/thread_share.js',
        embed_queueurl: 'http://netty0.disqus.com/queueurl.js',
        embed_hidereaction: 'http://netty0.disqus.com/hidereaction.js',
        embed_more_reactions: 'http://netty0.disqus.com/more_reactions.js',
        embed_subscribe: 'http://netty0.disqus.com/subscribe.js',
        embed_highlight: 'http://netty0.disqus.com/highlight.js',
        embed_block: 'http://netty0.disqus.com/block.js',
        update_moderate_all: 'http://netty0.disqus.com/update_moderate_all.js',
        update_days_alive: 'http://netty0.disqus.com/update_days_alive.js',
        show_user_votes: 'http://netty0.disqus.com/show_user_votes.js',
        forum_view: 'http://netty0.disqus.com/netty_new_and_noteworthy_documentationnew_and_noteworthy',
        cnn_saml_try: 'http://disqus.com/saml/cnn/try/',
        realtime: DISQUS.jsonData.settings.realtime_url,
        thread_view: 'http://netty0.disqus.com/thread/netty_new_and_noteworthy_documentationnew_and_noteworthy/',
        twitter_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/twitter/begin/',
        yahoo_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/yahoo/begin/',
        openid_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/openid/begin/',
        googleConnect: DISQUS.jsonData.settings.disqus_url + '/_ax/google/begin/',
        community: 'http://netty0.disqus.com/community.html',
        admin: 'http://netty0.disqus.com/admin/moderate/',
        moderate: 'http://netty0.disqus.com/admin/moderate/',
        moderate_threads: 'http://netty0.disqus.com/admin/moderate-threads/',
        settings: 'http://netty0.disqus.com/admin/settings/',
        unmerged_profiles: 'http://disqus.com/embed/profile/unmerged_profiles/',
        juggler: DISQUS.jsonData.settings.juggler_url,

        channels: {
            def:      'http://disqus.com/default.html', /* default channel */
            auth:     'https://disqus.com/embed/login.html',
            tweetbox: 'http://disqus.com/forums/integrations/twitter/tweetbox.html?f=netty0',
            edit:     'https://netty0.disqus.com/embed/editcomment.html'
        }
    };


    // 
    //     
    DISQUS.jsonData.urls.channels.reply = 'https://securecdn.disqus.com/1360872419/build/system/reply_ssl.html';
    DISQUS.jsonData.urls.channels.upload = 'https://securecdn.disqus.com/1360872419/build/system/upload_ssl.html';
    DISQUS.jsonData.urls.channels.sso = 'https://securecdn.disqus.com/1360872419/build/system/sso_ssl.html';
    DISQUS.jsonData.urls.channels.facebook = 'https://securecdn.disqus.com/1360872419/build/system/facebook_ssl.html';
    //     
    // 
}(window));
