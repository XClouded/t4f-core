document.write('<link href=\"https://gist.github.com/assets/embed-0af287a4b5c981db301049e56f06e5d3.css\" media=\"screen\" rel=\"stylesheet\" />')
document.write('<div id=\"gist3039492\" class=\"gist\">\n      <div class=\"gist-file\">\n        <div class=\"gist-data gist-syntax\">\n\n\n\n  <div class=\"file-data\">\n    <table cellpadding=\"0\" cellspacing=\"0\" class=\"lines highlight\">\n      <tr>\n        <td class=\"line-numbers\">\n          <span class=\"line-number\" id=\"file-gistfile1-java-L1\" rel=\"file-gistfile1-java-L1\">1<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L2\" rel=\"file-gistfile1-java-L2\">2<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L3\" rel=\"file-gistfile1-java-L3\">3<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L4\" rel=\"file-gistfile1-java-L4\">4<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L5\" rel=\"file-gistfile1-java-L5\">5<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L6\" rel=\"file-gistfile1-java-L6\">6<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L7\" rel=\"file-gistfile1-java-L7\">7<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L8\" rel=\"file-gistfile1-java-L8\">8<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L9\" rel=\"file-gistfile1-java-L9\">9<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L10\" rel=\"file-gistfile1-java-L10\">10<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L11\" rel=\"file-gistfile1-java-L11\">11<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L12\" rel=\"file-gistfile1-java-L12\">12<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L13\" rel=\"file-gistfile1-java-L13\">13<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L14\" rel=\"file-gistfile1-java-L14\">14<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L15\" rel=\"file-gistfile1-java-L15\">15<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L16\" rel=\"file-gistfile1-java-L16\">16<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L17\" rel=\"file-gistfile1-java-L17\">17<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L18\" rel=\"file-gistfile1-java-L18\">18<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L19\" rel=\"file-gistfile1-java-L19\">19<\/span>\n        <\/td>\n        <td class=\"line-data\">\n          <pre class=\"line-pre\"><div class=\"line\" id=\"file-gistfile1-java-LC1\"><span class=\"kd\">public<\/span> <span class=\"kd\">class<\/span> <span class=\"nc\">MyHandler<\/span> <span class=\"kd\">extends<\/span> <span class=\"n\">ChannelOutboundMessageHandlerAdapter<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC2\">    <span class=\"o\">...<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC3\">    <span class=\"kd\">public<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">flush<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelHandlerContext<\/span> <span class=\"n\">ctx<\/span><span class=\"o\">,<\/span> <span class=\"n\">ChannelFuture<\/span> <span class=\"n\">f<\/span><span class=\"o\">)<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC4\">        <span class=\"o\">...<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC5\">        <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">flush<\/span><span class=\"o\">(<\/span><span class=\"n\">f<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC6\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC7\">        <span class=\"c1\">// Schedule a write timeout.<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC8\">        <span class=\"n\">ctx<\/span><span class=\"o\">.<\/span><span class=\"na\">executor<\/span><span class=\"o\">().<\/span><span class=\"na\">schedule<\/span><span class=\"o\">(<\/span><span class=\"k\">new<\/span> <span class=\"n\">MyWriteTimeoutTask<\/span><span class=\"o\">(),<\/span> <span class=\"mi\">30<\/span><span class=\"o\">,<\/span> <span class=\"n\">TimeUnit<\/span><span class=\"o\">.<\/span><span class=\"na\">SECONDS<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC9\">        <span class=\"o\">...<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC10\">    <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC11\"><span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC12\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC13\"><span class=\"kd\">public<\/span> <span class=\"kd\">class<\/span> <span class=\"nc\">Main<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC14\">    <span class=\"kd\">public<\/span> <span class=\"kd\">static<\/span> <span class=\"kt\">void<\/span> <span class=\"nf\">main<\/span><span class=\"o\">(<\/span><span class=\"n\">String<\/span><span class=\"o\">[]<\/span> <span class=\"n\">args<\/span><span class=\"o\">)<\/span> <span class=\"kd\">throws<\/span> <span class=\"n\">Exception<\/span> <span class=\"o\">{<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC15\">        <span class=\"c1\">// Run an arbitrary task from an I/O thread.<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC16\">        <span class=\"n\">Channel<\/span> <span class=\"n\">ch<\/span> <span class=\"o\">=<\/span> <span class=\"o\">...;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC17\">        <span class=\"n\">ch<\/span><span class=\"o\">.<\/span><span class=\"na\">executor<\/span><span class=\"o\">().<\/span><span class=\"na\">execute<\/span><span class=\"o\">(<\/span><span class=\"k\">new<\/span> <span class=\"n\">Runnable<\/span><span class=\"o\">()<\/span> <span class=\"o\">{<\/span> <span class=\"o\">...<\/span> <span class=\"o\">});<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC18\">    <span class=\"o\">}<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC19\"><span class=\"o\">}<\/span><\/div><\/pre>\n        <\/td>\n      <\/tr>\n    <\/table>\n  <\/div>\n\n        <\/div>\n\n        <div class=\"gist-meta\">\n          <a href=\"https://gist.github.com/trustin/3039492/raw/417e4b3adf65e03745a43050e17aff805740da33/gistfile1.java\" style=\"float:right\">view raw<\/a>\n          <a href=\"https://gist.github.com/trustin/3039492#file-gistfile1-java\" style=\"float:right; margin-right:10px; color:#666;\">gistfile1.java<\/a>\n          <a href=\"https://gist.github.com/trustin/3039492\">This Gist<\/a> brought to you by <a href=\"http://github.com\">GitHub<\/a>.\n        <\/div>\n      <\/div>\n<\/div>\n')