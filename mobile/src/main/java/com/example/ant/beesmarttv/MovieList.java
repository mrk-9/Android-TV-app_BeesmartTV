 package com.example.ant.beesmarttv;

 import com.example.ant.beesmarttv.model.Channel;

 import java.util.ArrayList;

 public final class MovieList {

     public static ArrayList<Channel> list;

     public static ArrayList<Channel> setupMovies() {
         list = new ArrayList<Channel>();
         String title[] = {
                     "ABC TV",
                 "ABC 22 TV",
                 "CNN TV",
                 "CNBC TV",
                 "AlJazerra",
                 "Bloomberg",
                 "NFL TV",
                 "BTN TV",
                 "CBS TV",
                 "Fox NY TV",
                 "Fox Dayton, OH",
                 "Fox News TV",
                 "i24News TV",
                 "MTV",
                 "NASA TV",
                 "NBA TV",
                 "NBC TV",
                 "WGN 9 TV",
                 "Weather National TV",
                 "RT Documentary",
                 "RT News TV",
                 "WSVN 7News Miami",
                 "CW TV",
                 "TNT",
                 "Spike TV",
                 "USA Network",
                 "MSNBC TV",
                 "Nickelodeon TV",
                 "BET TV",
                 "Disney TV",
                 "ESPN TV",
                 "ESPN2 TV",
                 "Fox Sports TV",
                 "SkySports1",
                 "SkySports2",
                 "SkySports3",
                 "SkySports4",
                 "SkySports5",
                 "SkySportsF1",
                 "A&E TV",
                 "TLC TV",
                 "VH1",
                 "Food Network TV",
                 "History TV",
                 "SyFy TV",
                 "Cartoon Network Tv",
                 "Travel TV",
                 "HG TV",
                 "Bravo",
                 "Tru TV",
                 "Comedy Central Tv",
                 "National Geographics",
                 "Investigation Discovary"
         };

         String videoUrl[] = {
                 "http://185.63.255.33:1935/liveedge/rGCi7bg0Qxvs8q7Sl13l/chunklist_w1279479792.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/nz7l9vc5nv1og7b/playlist.m3u8?xs=_we_bno3bDl2YzVudjFvZzdifDE0NDM3Mzg0OTF8fDU2MGM2MWZiN2ZjZmV8Zjk0NjU1ZGZiNjdkMWY0MDM5Mjc3ODk3MDQ2OTM1NmI1YWQ2OGYyYw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/rjxo8hz17q9waiq/playlist.m3u8?xs=_we_cmp4bzhoejE3cTl3YWlxfDE0NDM3NDEzMzR8fDU2MGM2ZDE2MTBkYjF8MmU1ZGEzODk1N2ViM2MxZDZiMmIzMWU2MjM5MjhkZWZmZjA2YWI3MQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/xssrqbmovted7h6/playlist.m3u8?xs=_we_eHNzcnFibW92dGVkN2g2fDE0NDM3NDMwNTZ8fDU2MGM3M2QwNTYzZGV8OWIzYzIzYjIyYmU2NzhiMGFhZjVjYjdiNDNjZTdiN2Q2YmZjZjMwZQ..",
                 "http://wpc.c1a9.edgecastcdn.net/hls-live/20C1A9/aljazeera_en/ls_satlink/b_528.m3u8",
                 "http://cdn3.videos.bloomberg.com/btv/us/master.m3u8?b?b*t$%20timeout=10#.m3u8",
                 "http://nflsvglagame1-i.akamaihd.net/hls/live/223206/NFL_Mobile/2015Mobile_NFLN_2000k.m3u8",
                 "http://bigten247.cdnak.bigtenhd.neulion.com/nlds/btn2go/btnnetwork/as/live/btnnetwork_hd_1240.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/vp4q2gonwzh2dku/playlist.m3u8?xs=_we_dnA0cTJnb253emgyZGt1fDE0NDM3Mzg2OTB8fDU2MGM2MmMyMmE5NDJ8YjY5MGJkNjg4YTg4YzdhNDhkZTViYjJlNDdiZjUyYTViYjgwZWE4Zg..",
                 "http://185.63.255.20:1935/liveedge/9pxGCtP3aEnJYgoBFFH8/chunklist_w1334095898.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/yuu6p5oaa1x4a1p/playlist.m3u8?xs=_we_eXV1NnA1b2FhMXg0YTFwfDE0NDM3NDA2Njd8fDU2MGM2YTdiYzkyM2V8Yjk3OTA3YjlhODIxN2EwNjFlMWQ0NDUwMDRlZWY3NWIyYWVmZTgxZQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/ohn58e97er36vjr/playlist.m3u8?xs=_we_b2huNThlOTdlcjM2dmpyfDE0NDM3NDEzOTd8fDU2MGM2ZDU1MmQ3NjZ8N2FjODExNTViYWUyNGM4ZDU5M2NlOGUyM2MyNDA5NDIxODI4YmVlMg..",
                 "http://bcoveliveios-i.akamaihd.net/hls/live/215102/master_french/412/master.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/p0audpgrkvu6l1g/playlist.m3u8?xs=_we_cDBhdWRwZ3JrdnU2bDFnfDE0NDM3NDMwNzd8fDU2MGM3M2U1OTNmMDJ8N2U2MTliNjE3NmMwODIyMmY4ZTMwNzMyYjM0NjU0MWMzZDEzZGQ3OA..",
                 "http://nasatv-lh.akamaihd.net/i/NASA_101@319270/master.m3u8",
                 "http://s3.iptvapi.com:8081/NBA/index.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/kjgkefh6la1di6g/playlist.m3u8?xs=_we_a2pna2VmaDZsYTFkaTZnfDE0NDM3NDA2Mzd8fDU2MGM2YTVkZGFlNjR8ZGRmN2E4MTA0YTUyZDZhOTQ2NzczNDg3ZGI0MjkzMWFjMTc5YjNjYw..",
                 "http://wgntribune-lh.akamaihd.net/i/WGNPrimary_1@304622/master.m3u8",
                 "http://cdnapi.kaltura.com/p/931702/sp/93170200/playManifest/entryId/1_oorxcge2/format/applehttp/protocol/http/uiConfId/28428751/a.m3u8",
                 "http://rt.ashttp14.visionip.tv/live/rt-doc-live-HD/playlist.m3u8",
                 "http://rt.ashttp14.visionip.tv/live/rt-global-live-HD/playlist.m3u8",
                 "http://wsvn.mpl.miisolutions.net:1935/wsvn-live01/_definst_/wsvn_1044/playlist.m3u8",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/qvvkvojlano9hnj/playlist.m3u8?xs=_we_cXZ2a3Zvamxhbm85aG5qfDE0NDM3NDA3MDB8fDU2MGM2YTljZDg2YTh8NmU0MjI3NGU3OTE1Yzk4ZDc3N2U5ODkwOGU5NzdlNDcxZmUxZTE2Ng..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/7uz4als3nigi0j3/playlist.m3u8?xs=_we_N3V6NGFsczNuaWdpMGozfDE0NDM3NDA3NTJ8fDU2MGM2YWQwYmI1YTd8ODA5YTBjYWMxNjNmZWI4MmI3NTk1ZjUwYTlmMDFiYWRkZDdjNjI3OQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/sax2mq2alpirnww/playlist.m3u8?xs=_we_c2F4Mm1xMmFscGlybnd3fDE0NDM3NDA4MDB8fDU2MGM2YjAwOTVkOTh8MzNmZDBmYzAwMTQ2YmQxYWFjZmE4OWU2NmVhMWZhZDUyNGU2YTdhYg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/h5eovaj1jeegluq/playlist.m3u8?xs=_we_aDVlb3ZhajFqZWVnbHVxfDE0NDM3NDA4MjZ8fDU2MGM2YjFhYzAyMTd8NGRjYTJiNWVlOWY4MzJmMTY5MmMwZDdjODRlZTI1ZGFmMzg0MjRjNQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/1341ujtliqndod8/playlist.m3u8?xs=_we_MTM0MXVqdGxpcW5kb2Q4fDE0NDM3NDA5NTV8fDU2MGM2YjliNDQ1YjV8ZDBkM2U0MGI4NDNlNDJhMWRhYWM0ZjA5ZGEzYjZkNGU3YTljMTZlZA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/64jaber5pjqj2cr/playlist.m3u8?xs=_we_NjRqYWJlcjVwanFqMmNyfDE0NDM3NDE1MDN8fDU2MGM2ZGJmODI3MjR8YzBmYjlmNmYyYTMyZDU3YzE5NGJiYzRmMzYxNWU4ZjNiMzM4NDNjZA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/kexkxje3nqi6dtl/playlist.m3u8?xs=_we_a2V4a3hqZTNucWk2ZHRsfDE0NDM3NDE1OTl8fDU2MGM2ZTFmNjlhOTN8OTliMDMxNjM3MWRhYmY0MWEwMTdlMzFhZjBkYjE2YzVmMTY3Nzk0Nw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/2tag4dzgwof5ma2/playlist.m3u8?xs=_we_MnRhZzRkemd3b2Y1bWEyfDE0NDM3NDE2OTh8fDU2MGM2ZTgyMjgxZjR8ZjQ5MjJiOWZkMjJiZGRmN2U5ZjJmNDFhNzA1YzNhZTFjMTgzYWJmYQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/uf6gc0ii1wgjwy4/playlist.m3u8?xs=_we_dWY2Z2MwaWkxd2dqd3k0fDE0NDM3NDE3NDR8fDU2MGM2ZWIwYjZkNTN8NWNjNjRlODc4MTVjZGVhMmZlNWNhNmFiZGY0NTA0NmZjNjgzODFhZA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/rgyus7ao2ullmbt/playlist.m3u8?xs=_we_cmd5dXM3YW8ydWxsbWJ0fDE0NDM3NDE4MDl8fDU2MGM2ZWYxYmQ5OTV8YjM2YTY0Y2I4MzYyMmVlODFlM2EwZDdkNWNmNzRiYzZlZjIxNDNiNQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/pgbt2gfuifeli72/playlist.m3u8?xs=_we_cGdidDJnZnVpZmVsaTcyfDE0NDM3NDIzNzB8fDU2MGM3MTIyNjYxNzd8OGRlMWE3NzM2ZGQ2NDVhODMyMzA5NGRkMWRmMGVmMmZlY2ZmNWYwMw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/9ddlpiijjci12o9/playlist.m3u8?xs=_we_OWRkbHBpaWpqY2kxMm85fDE0NDM3NDI3MDN8fDU2MGM3MjZmMGVhMzB8NmJhNzBlODg5NTkwN2NjMmRkZjE2MWU5MjM1MTQ1MzYxNzUzNDdmYw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/on39ol6in4f2ewp/playlist.m3u8?xs=_we_b24zOW9sNmluNGYyZXdwfDE0NDM3NDI3Nzl8fDU2MGM3MmJiMDJlMDh8MGY0Y2IyMmE0NzBhYTQzYmQzN2Y2MTYyZDIyOTcwZWQ4ZWFjZWQ3Mw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/pdqdnbyfwtzknat/playlist.m3u8?xs=_we_cGRxZG5ieWZ3dHprbmF0fDE0NDM3NDI5NjR8fDU2MGM3Mzc0ODBjZDh8ODRkMjExZWE1ZGY1MTE2YmM5Mzc5OGVlYTk2YTAxNDYyMWMzMTRlNA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/21ajqprn56aebp3/playlist.m3u8?xs=_we_MjFhanFwcm41NmFlYnAzfDE0NDM3NDI5MTN8fDU2MGM3MzQxZjA2ZGN8NDcwZTlhMzJmNDdmNGY5NGFiY2RkZjkwYjExOWE0NzVkZDlkY2Q0ZA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/2qjxgeic3x5rkdu/playlist.m3u8?xs=_we_MnFqeGdlaWMzeDVya2R1fDE0NDM3NDI5NzF8fDU2MGM3MzdiNDdjYTB8YjkzYTMwNWY1ZjllNjE5ZTNlMmQxMDAwMTk2YzBiMzZhODhkMGNkYQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/udhfbfg0vfzjcds/playlist.m3u8?xs=_we_dWRoZmJmZzB2ZnpqY2RzfDE0NDM3NDI4ODd8fDU2MGM3MzI3MjU0MTR8MWFkMTAwYmQyZmNkNTgxNmRlZTljM2MxMzM2Y2EyMDliNjM1ZDA4OA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/tuc280rbycwnsvs/playlist.m3u8?xs=_we_dHVjMjgwcmJ5Y3duc3ZzfDE0NDM3NDMwMTd8fDU2MGM3M2E5MmY4Nzd8Njk2MzQ4MzJiNTRlMjhlYjlmNmZmMzQyMDliODliNTdiODY4OTQ3Mg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/iugceoy4o6ljhck/playlist.m3u8?xs=_we_aXVnY2VveTRvNmxqaGNrfDE0NDM3NDMwMzZ8fDU2MGM3M2JjYmQzZjh8YjJjYjllNjY4YzEzMzE0YzYxMzQ0ODNhZjhiZDU5ZWUzOTA3ODQxNg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/oeiigxxv1gbeqq9/playlist.m3u8?xs=_we_b2VpaWd4eHYxZ2JlcXE5fDE0NDM3NDMwOTR8fDU2MGM3M2Y2ZDkyY2Z8MDk2MjljODEwNWE4NGQ2ZTdiM2YzYmE4MTI0YTU1NzNjZTExMTNiYg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/ibnnu7537h3ax6e/playlist.m3u8?xs=_we_aWJubnU3NTM3aDNheDZlfDE0NDM3NDMxMTZ8fDU2MGM3NDBjOWNmODd8YjIzMDkyZDc0ZTk3NTIyZjlhNGQ3YWIxMGQ3MTUxMTgzNDAwMzMzMw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/dhp0rpd116qyyj6/playlist.m3u8?xs=_we_ZGhwMHJwZDExNnF5eWo2fDE0NDM3NDMxMzh8fDU2MGM3NDIyOWRmZTB8NWFhOTBiMjE1NjMxYjFjY2I5NWVkYWI4YTRkYzFiN2NhNDlkMDZmMg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/x7uii7mll2b7lh4/playlist.m3u8?xs=_we_eDd1aWk3bWxsMmI3bGg0fDE0NDM3NDMxNzB8fDU2MGM3NDQyYzEzOGF8NDg2MzdiM2FmYWQzMmNiYmQ4YzE1MzkzYTU0NjM3MWNiZWExOWUzYw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/9l41fcr1sgmclej/playlist.m3u8?xs=_we_OWw0MWZjcjFzZ21jbGVqfDE0NDM3NDMxOTJ8fDU2MGM3NDU4NDQxYjZ8YTExOGRkZmQ5ZTE2MmEyNzEzY2I2YTU2YWZjOGY1MTQ0MTRkYjI1OA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/4aiufk0o430qt0d/playlist.m3u8?xs=_we_NGFpdWZrMG80MzBxdDBkfDE0NDM3NDMyMzh8fDU2MGM3NDg2MTkzMTd8NGYyNDA1OWRjMWFjNmY4MjVhZTcxNTdmOTRhMWVlMWIyM2JhODk5Mg..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/uw433ikqnrj501l/playlist.m3u8?xs=_we_dXc0MzNpa3Fucmo1MDFsfDE0NDM3NDMyNjF8fDU2MGM3NDlkN2JlMmR8NGVjNmI1Y2Y2ZjE4NzI4OTY2MTIxMWI5NjA3OTBlMjYzMGI3YjE5Yw..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/uhnr4161ybioa5w/playlist.m3u8?xs=_we_dWhucjQxNjF5YmlvYTV3fDE0NDM3NDMyODV8fDU2MGM3NGI1MGM1OTB8MzAyZWVkNTA1NmIwMGVmYjZkN2NkNjM5YjQ0YWNmZWFlOTE0NGUwOQ..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/gj65q2rg18tpmut/playlist.m3u8?xs=_we_Z2o2NXEycmcxOHRwbXV0fDE0NDM3NDQzMzJ8fDU2MGM3OGNjOTI1ZmJ8NTIxYTlkOGQ0NDA4N2M1OTU1MzNiZGRmYmYyMjVmMjZmYjQ4MDQxZA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/wyw9tfxexu68p0t/playlist.m3u8?xs=_we_d3l3OXRmeGV4dTY4cDB0fDE0NDM3NDMzMzN8fDU2MGM3NGU1N2U1NGV8ZjUyZTJjN2ZjMTkxZjI0NDdmZTFmMmIyMDJmNzY3NmYxYTlkMmI2OA..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/qzsfigd1y3bgbhs/playlist.m3u8?xs=_we_cXpzZmlnZDF5M2JnYmhzfDE0NDM3NDM0MDl8fDU2MGM3NTMxNzY4NTF8ZmZkYmNjZjk0MDQ0NjNkODQzMzZhZWNlY2M4MWQyZmE4MDIwMTI4Ng..",
                 "http://streamlivepremium.9origin.com:1935/edge/_definst_/yjma618cibkqxeo/playlist.m3u8?xs=_we_eWptYTYxOGNpYmtxeGVvfDE0NDM3NDQzMDh8fDU2MGM3OGI0Y2IxOGV8MzIzZjlmMDQ1MjE5YWVlMTkxZTUzMTRhNjkwY2U4ZjVkNDg1YzZmMg.."
         };

         int bgImageUrl[] = {
                 R.drawable.abc_tv_icon, R.drawable.abc22_tv_icon, R.drawable.cnn_tv_icon, R.drawable.cnbc_tv_icon, R.drawable.aljazerra_tv_icon, R.drawable.bloomberg_tv_icon, R.drawable.nfl_tv_icon, R.drawable.big_ten_tv_icon, R.drawable.cbs_tv_icon, R.drawable.fox_tv_icon, R.drawable.fox_dayton_tv_icon, R.drawable.fox_news_icon, R.drawable.i24_tv_icon, R.drawable.mtv_icon, R.drawable.nasa_tv_icon, R.drawable.nba_tv_icon, R.drawable.nbc_tv_icon, R.drawable.wgn9_tv_icon, R.drawable.weather_tv_icon, R.drawable.rt_document_tv_icon, R.drawable.rt_news_tv_icon, R.drawable.wsn_tv_icon, R.drawable.cw_tv_icon, R.drawable.tnt_tv_icon, R.drawable.spike_tv_icon, R.drawable.usa_network_tv_icon, R.drawable.msnbc_tv_icon, R.drawable.nick_tv_icon, R.drawable.bet_tv_icon, R.drawable.disney_tv_icon, R.drawable.estv_tv_icon, R.drawable.estv2_tv_icon, R.drawable.fox_sport_tv_icon,
                 R.drawable.sky_sport1_tv_icon, R.drawable.sky_sport2_tv_icon, R.drawable.sky_sport_tv_icon, R.drawable.sky_sport4_tv_icon, R.drawable.sky_sport5_tv_icon, R.drawable.sky_sportf1_tv_icon, R.drawable.ae_tv_icon, R.drawable.tlc_tv_icon, R.drawable.vh1_tv_icon, R.drawable.food_network_tv_icon, R.drawable.hsn_tv_icon, R.drawable.syfy_tv_icon, R.drawable.cartoon_tv_icon, R.drawable.travel_tv_icon, R.drawable.hg_tv_icon, R.drawable.bravo_tv_icon, R.drawable.tru_tv_icon, R.drawable.comedy_tv_icon, R.drawable.national_geographics_tv_icon, R.drawable.investigation_tv_icon
         };

         for (int i = 0; i < 53; i ++)  {
             list.add(buildMovieInfo(title[i], videoUrl[i], bgImageUrl[i]));
         }

         return list;
     }

     private static Channel buildMovieInfo(String channle_name, String channel_link, int channel_icon) {
         Channel channel = new Channel();

         channel.setChannel_icon(channel_icon);
         channel.setChannel_link(channel_link);
         channel.setChannel_name(channle_name);
         return channel;
     }
 }