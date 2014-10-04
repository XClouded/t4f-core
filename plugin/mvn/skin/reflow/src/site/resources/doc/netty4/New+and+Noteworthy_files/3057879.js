document.write('<link href=\"https://gist.github.com/assets/embed-0af287a4b5c981db301049e56f06e5d3.css\" media=\"screen\" rel=\"stylesheet\" />')
document.write('<div id=\"gist3057879\" class=\"gist\">\n      <div class=\"gist-file\">\n        <div class=\"gist-data gist-syntax\">\n\n\n\n  <div class=\"file-data\">\n    <table cellpadding=\"0\" cellspacing=\"0\" class=\"lines highlight\">\n      <tr>\n        <td class=\"line-numbers\">\n          <span class=\"line-number\" id=\"file-gistfile1-java-L1\" rel=\"file-gistfile1-java-L1\">1<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L2\" rel=\"file-gistfile1-java-L2\">2<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L3\" rel=\"file-gistfile1-java-L3\">3<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L4\" rel=\"file-gistfile1-java-L4\">4<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L5\" rel=\"file-gistfile1-java-L5\">5<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L6\" rel=\"file-gistfile1-java-L6\">6<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L7\" rel=\"file-gistfile1-java-L7\">7<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L8\" rel=\"file-gistfile1-java-L8\">8<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L9\" rel=\"file-gistfile1-java-L9\">9<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L10\" rel=\"file-gistfile1-java-L10\">10<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L11\" rel=\"file-gistfile1-java-L11\">11<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L12\" rel=\"file-gistfile1-java-L12\">12<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L13\" rel=\"file-gistfile1-java-L13\">13<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L14\" rel=\"file-gistfile1-java-L14\">14<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L15\" rel=\"file-gistfile1-java-L15\">15<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L16\" rel=\"file-gistfile1-java-L16\">16<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L17\" rel=\"file-gistfile1-java-L17\">17<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L18\" rel=\"file-gistfile1-java-L18\">18<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L19\" rel=\"file-gistfile1-java-L19\">19<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L20\" rel=\"file-gistfile1-java-L20\">20<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L21\" rel=\"file-gistfile1-java-L21\">21<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L22\" rel=\"file-gistfile1-java-L22\">22<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L23\" rel=\"file-gistfile1-java-L23\">23<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L24\" rel=\"file-gistfile1-java-L24\">24<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L25\" rel=\"file-gistfile1-java-L25\">25<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L26\" rel=\"file-gistfile1-java-L26\">26<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L27\" rel=\"file-gistfile1-java-L27\">27<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L28\" rel=\"file-gistfile1-java-L28\">28<\/span>\n        <\/td>\n        <td class=\"line-data\">\n          <pre class=\"line-pre\"><div class=\"line\" id=\"file-gistfile1-java-LC1\">    <span class=\"kd\">private<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">sendNumbers<\/span><span class=\"o\">()<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC2\">        <span class=\"c1\">// Do not send more than 4096 numbers.<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC3\">        <span class=\"kt\">boolean<\/span> <span class=\"n\">finished<\/span> <span class=\"o\">=<\/span> <span class=\"kc\">false<\/span><span class=\"o\">;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC4\">        <span class=\"n\">MessageBuf<\/span><span class=\"o\">&lt;<\/span><span class=\"n\">Object<\/span><span class=\"o\">&gt;<\/span> <span class=\"n\">out<\/span> <span class=\"o\">=<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">nextOutboundMessageBuffer<\/span><span class=\"o\">();<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC5\">        <span class=\"k\">while<\/span> <span class=\"o\">(<\/span><span class=\"n\">out<\/span><span class=\"o\">.<\/span><span class=\"na\">size<\/span><span class=\"o\">()<\/span> <span class=\"o\">&lt;<\/span> <span class=\"mi\">4096<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC6\">            <span class=\"k\">if<\/span> <span class=\"o\">(<\/span><span class=\"n\">i<\/span> <span class=\"o\">&lt;=<\/span> <span class=\"n\">count<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC7\">                <span class=\"n\">out<\/span><span class=\"o\">.<\/span><span class=\"na\">add<\/span><span class=\"o\">(<\/span><span class=\"n\">Integer<\/span><span class=\"o\">.<\/span><span class=\"na\">valueOf<\/span><span class=\"o\">(<\/span><span class=\"n\">i<\/span><span class=\"o\">));<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC8\">                <span class=\"n\">i<\/span> <span class=\"o\">++;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC9\">            <span class=\"o\">}<\/span> <span class=\"k\">else<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC10\">                <span class=\"n\">finished<\/span> <span class=\"o\">=<\/span> <span class=\"kc\">true<\/span><span class=\"o\">;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC11\">                <span class=\"k\">break<\/span><span class=\"o\">;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC12\">            <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC13\">        <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC14\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC15\">        <span class=\"n\">ChannelFuture<\/span> <span class=\"n\">f<\/span> <span class=\"o\">=<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">flush<\/span><span class=\"o\">();<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC16\">        <span class=\"k\">if<\/span> <span class=\"o\">(!<\/span><span class=\"n\">finished<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC17\">            <span class=\"n\">f<\/span><span class=\"o\">.<\/span><span class=\"na\">addListener<\/span><span class=\"o\">(<\/span><span class=\"n\">SEND_NUMBERS<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC18\">        <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC19\">    <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC20\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC21\">    <span class=\"kd\">private<\/span> <span class=\"kd\">final<\/span> <span class=\"n\">ChannelFutureListener<\/span> <span class=\"n\">SEND_NUMBERS<\/span> <span class=\"o\">=<\/span> <span class=\"k\">new<\/span> <span class=\"n\">ChannelFutureListener<\/span><span class=\"o\">()<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC22\">        <span class=\"nd\">@Override<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC23\">        <span class=\"kd\">public<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">operationComplete<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelFuture<\/span> <span class=\"n\">future<\/span><span class=\"o\">)<\/span> <span class=\"kd\">throws<\/span> <span class=\"n\">Exception<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC24\">            <span class=\"k\">if<\/span> <span class=\"o\">(<\/span><span class=\"n\">future<\/span><span class=\"o\">.<\/span><span class=\"na\">isSuccess<\/span><span class=\"o\">())<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC25\">                <span class=\"n\">sendNumbers<\/span><span class=\"o\">();<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC26\">            <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC27\">        <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC28\">    <span class=\"o\">};<\/span><\/div><\/pre>\n        <\/td>\n      <\/tr>\n    <\/table>\n  <\/div>\n\n        <\/div>\n\n        <div class=\"gist-meta\">\n          <a href=\"https://gist.github.com/trustin/3057879/raw/503939143d885437b0fc416cd10073a95f903571/gistfile1.java\" style=\"float:right\">view raw<\/a>\n          <a href=\"https://gist.github.com/trustin/3057879#file-gistfile1-java\" style=\"float:right; margin-right:10px; color:#666;\">gistfile1.java<\/a>\n          <a href=\"https://gist.github.com/trustin/3057879\">This Gist<\/a> brought to you by <a href=\"http://github.com\">GitHub<\/a>.\n        <\/div>\n      <\/div>\n<\/div>\n')
