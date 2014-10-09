document.write('<link href=\"https://gist.github.com/assets/embed-0af287a4b5c981db301049e56f06e5d3.css\" media=\"screen\" rel=\"stylesheet\" />')
document.write('<div id=\"gist3057755\" class=\"gist\">\n      <div class=\"gist-file\">\n        <div class=\"gist-data gist-syntax\">\n\n\n\n  <div class=\"file-data\">\n    <table cellpadding=\"0\" cellspacing=\"0\" class=\"lines highlight\">\n      <tr>\n        <td class=\"line-numbers\">\n          <span class=\"line-number\" id=\"file-gistfile1-java-L1\" rel=\"file-gistfile1-java-L1\">1<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L2\" rel=\"file-gistfile1-java-L2\">2<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L3\" rel=\"file-gistfile1-java-L3\">3<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L4\" rel=\"file-gistfile1-java-L4\">4<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L5\" rel=\"file-gistfile1-java-L5\">5<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L6\" rel=\"file-gistfile1-java-L6\">6<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L7\" rel=\"file-gistfile1-java-L7\">7<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L8\" rel=\"file-gistfile1-java-L8\">8<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L9\" rel=\"file-gistfile1-java-L9\">9<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L10\" rel=\"file-gistfile1-java-L10\">10<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L11\" rel=\"file-gistfile1-java-L11\">11<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L12\" rel=\"file-gistfile1-java-L12\">12<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L13\" rel=\"file-gistfile1-java-L13\">13<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L14\" rel=\"file-gistfile1-java-L14\">14<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L15\" rel=\"file-gistfile1-java-L15\">15<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L16\" rel=\"file-gistfile1-java-L16\">16<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L17\" rel=\"file-gistfile1-java-L17\">17<\/span>\n        <\/td>\n        <td class=\"line-data\">\n          <pre class=\"line-pre\"><div class=\"line\" id=\"file-gistfile1-java-LC1\"><span class=\"kd\">public<\/span> <span class=\"kd\">class<\/span> <span class=\"nc\">MyHandler<\/span> <span class=\"kd\">extends<\/span> <span class=\"n\">ChannelInboundMessageHandlerAdapter<\/span><span class=\"o\">&lt;<\/span><span class=\"n\">MyMessage<\/span><span class=\"o\">&gt;<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC2\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC3\">    <span class=\"kd\">private<\/span> <span class=\"kd\">static<\/span> <span class=\"kd\">final<\/span> <span class=\"n\">AttributeKey<\/span><span class=\"o\">&lt;<\/span><span class=\"n\">MyState<\/span><span class=\"o\">&gt;<\/span> <span class=\"n\">STATE<\/span> <span class=\"o\">=<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC4\">            <span class=\"k\">new<\/span> <span class=\"n\">AttributeKey<\/span><span class=\"o\">&lt;<\/span><span class=\"n\">MyState<\/span><span class=\"o\">&gt;(<\/span><span class=\"s\">&quot;MyHandler.state&quot;<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC5\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC6\">    <span class=\"nd\">@Override<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC7\">    <span class=\"kd\">public<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">channelRegistered<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelHandlerContext<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC8\">        <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">attr<\/span><span class=\"o\">(<\/span><span class=\"n\">STATE<\/span><span class=\"o\">).<\/span><span class=\"na\">set<\/span><span class=\"o\">(<\/span><span class=\"k\">new<\/span> <span class=\"n\">MyState<\/span><span class=\"o\">());<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC9\">        <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">fireChannelRegistered<\/span><span class=\"o\">();<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC10\">    <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC11\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC12\">    <span class=\"nd\">@Override<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC13\">    <span class=\"kd\">public<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">messageReceived<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelHandlerContext<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">,<\/span> <span class=\"n\">MyMessage<\/span> <span class=\"n\">msg<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC14\">        <span class=\"n\">MyState<\/span> <span class=\"n\">state<\/span> <span class=\"o\">=<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">attr<\/span><span class=\"o\">(<\/span><span class=\"n\">STATE<\/span><span class=\"o\">).<\/span><span class=\"na\">get<\/span><span class=\"o\">();<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC15\">    <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC16\">    <span class=\"o\">...<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC17\"><span class=\"o\">}<\/span><\/div><\/pre>\n        <\/td>\n      <\/tr>\n    <\/table>\n  <\/div>\n\n        <\/div>\n\n        <div class=\"gist-meta\">\n          <a href=\"https://gist.github.com/trustin/3057755/raw/97c28bc578e1f147c8cccc8386125795c75b4a5a/gistfile1.java\" style=\"float:right\">view raw<\/a>\n          <a href=\"https://gist.github.com/trustin/3057755#file-gistfile1-java\" style=\"float:right; margin-right:10px; color:#666;\">gistfile1.java<\/a>\n          <a href=\"https://gist.github.com/trustin/3057755\">This Gist<\/a> brought to you by <a href=\"http://github.com\">GitHub<\/a>.\n        <\/div>\n      <\/div>\n<\/div>\n')