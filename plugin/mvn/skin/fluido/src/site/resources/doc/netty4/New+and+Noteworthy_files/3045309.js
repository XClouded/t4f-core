document.write('<link href=\"https://gist.github.com/assets/embed-0af287a4b5c981db301049e56f06e5d3.css\" media=\"screen\" rel=\"stylesheet\" />')
document.write('<div id=\"gist3045309\" class=\"gist\">\n      <div class=\"gist-file\">\n        <div class=\"gist-data gist-syntax\">\n\n\n\n  <div class=\"file-data\">\n    <table cellpadding=\"0\" cellspacing=\"0\" class=\"lines highlight\">\n      <tr>\n        <td class=\"line-numbers\">\n          <span class=\"line-number\" id=\"file-gistfile1-java-L1\" rel=\"file-gistfile1-java-L1\">1<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L2\" rel=\"file-gistfile1-java-L2\">2<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L3\" rel=\"file-gistfile1-java-L3\">3<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L4\" rel=\"file-gistfile1-java-L4\">4<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L5\" rel=\"file-gistfile1-java-L5\">5<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L6\" rel=\"file-gistfile1-java-L6\">6<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L7\" rel=\"file-gistfile1-java-L7\">7<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L8\" rel=\"file-gistfile1-java-L8\">8<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L9\" rel=\"file-gistfile1-java-L9\">9<\/span>\n          <span class=\"line-number\" id=\"file-gistfile1-java-L10\" rel=\"file-gistfile1-java-L10\">10<\/span>\n        <\/td>\n        <td class=\"line-data\">\n          <pre class=\"line-pre\"><div class=\"line\" id=\"file-gistfile1-java-LC1\"><span class=\"n\">ChannelConfig<\/span> <span class=\"n\">cfg<\/span> <span class=\"o\">=<\/span> <span class=\"o\">...;<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC2\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC3\"><span class=\"c1\">// Before:<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC4\"><span class=\"n\">cfg<\/span><span class=\"o\">.<\/span><span class=\"na\">setOption<\/span><span class=\"o\">(<\/span><span class=\"s\">&quot;tcpNoDelay&quot;<\/span><span class=\"o\">,<\/span> <span class=\"kc\">true<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC5\"><span class=\"n\">cfg<\/span><span class=\"o\">.<\/span><span class=\"na\">setOption<\/span><span class=\"o\">(<\/span><span class=\"s\">&quot;tcpNoDelay&quot;<\/span><span class=\"o\">,<\/span> <span class=\"mi\">0<\/span><span class=\"o\">);<\/span>  <span class=\"c1\">// Runtime ClassCastException<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC6\"><span class=\"n\">cfg<\/span><span class=\"o\">.<\/span><span class=\"na\">setOption<\/span><span class=\"o\">(<\/span><span class=\"s\">&quot;tcpNoDelays&quot;<\/span><span class=\"o\">,<\/span> <span class=\"kc\">true<\/span><span class=\"o\">);<\/span> <span class=\"c1\">// Typo in the option name - ignored silently<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC7\">&nbsp;<\/div><div class=\"line\" id=\"file-gistfile1-java-LC8\"><span class=\"c1\">// After:<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC9\"><span class=\"n\">cfg<\/span><span class=\"o\">.<\/span><span class=\"na\">setOption<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelOption<\/span><span class=\"o\">.<\/span><span class=\"na\">TCP_NODELAY<\/span><span class=\"o\">,<\/span> <span class=\"kc\">true<\/span><span class=\"o\">);<\/span><\/div><div class=\"line\" id=\"file-gistfile1-java-LC10\"><span class=\"n\">cfg<\/span><span class=\"o\">.<\/span><span class=\"na\">setOption<\/span><span class=\"o\">(<\/span><span class=\"n\">ChannelOption<\/span><span class=\"o\">.<\/span><span class=\"na\">TCP_NODELAY<\/span><span class=\"o\">,<\/span> <span class=\"mi\">0<\/span><span class=\"o\">);<\/span> <span class=\"c1\">// Compile error<\/span><\/div><\/pre>\n        <\/td>\n      <\/tr>\n    <\/table>\n  <\/div>\n\n        <\/div>\n\n        <div class=\"gist-meta\">\n          <a href=\"https://gist.github.com/trustin/3045309/raw/836c009e3fb0d1e843e516eddd133e93a00eef97/gistfile1.java\" style=\"float:right\">view raw<\/a>\n          <a href=\"https://gist.github.com/trustin/3045309#file-gistfile1-java\" style=\"float:right; margin-right:10px; color:#666;\">gistfile1.java<\/a>\n          <a href=\"https://gist.github.com/trustin/3045309\">This Gist<\/a> brought to you by <a href=\"http://github.com\">GitHub<\/a>.\n        <\/div>\n      <\/div>\n<\/div>\n')