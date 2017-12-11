package com.lindar.emailnormalize;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.regex.Pattern;

public class Email {
    private static final Options DEFAULT_OPTIONS = new Options.OptionsBuilder()
            .forceRemoveDots(false)
            .forceRemoveTags(false)
            .build();

    private static final Pattern MATCH_GMAIL_PATTERN = Pattern.compile("^(gmail|googlemail|google)\\.com$");

    private static Map<String, String> domainsWithTag = ImmutableMap.<String, String>builder()
            // Google only has two Gmail domains: https://en.wikipedia.org/wiki/List_of_Google_domains
            .put("gmail.com", "\\+")
            .put("googlemail.com", "\\+")
            // corporate email addresses; TODO presumably country domains also receive corporate email?
            .put("google.com'", ",")
            // Microsoft
            .put("outlook.com", "\\+")
            .put("hotmail.com", "\\+")
            .put("live.com", "\\+")
            // Fastmail - https://www.fastmail.com/help/receive/addressing.html TODO: whatever@username.fastmail.com -> username@fastmail.com
            .put("fastmail.com", "\\+")
            .put("fastmail.fm", "\\+")
            // Yahoo Mail Plus accounts, per https://en.wikipedia.org/wiki/Yahoo!_Mail#Email_domains, use hyphens - http://www.cnet.com/forums/discussions/did-yahoo-break-disposable-email-addresses-mail-plus-395088/
            .put("yahoo.com.ar", "-")
            .put("yahoo.com.au", "-")
            .put("yahoo.at", "-")
            .put("yahoo.be/fr", "-")
            .put("yahoo.be/nl", "-")
            .put("yahoo.com.br", "-")
            .put("ca.yahoo.com", "-")
            .put("qc.yahoo.com", "-")
            .put("yahoo.com.co", "-")
            .put("yahoo.com.hr", "-")
            .put("yahoo.cz", "-")
            .put("yahoo.dk", "-")
            .put("yahoo.fi", "-")
            .put("yahoo.fr", "-")
            .put("yahoo.de", "-")
            .put("yahoo.gr", "-")
            .put("yahoo.com.hk", "-")
            .put("yahoo.hu", "-")
            .put("yahoo.co.in/yahoo.in", "-")
            .put("yahoo.co.id", "-")
            .put("yahoo.ie", "-")
            .put("yahoo.co.il", "-")
            .put("yahoo.it", "-")
            .put("yahoo.co.jp", "-")
            .put("yahoo.com.my", "-")
            .put("yahoo.com.mx", "-")
            .put("yahoo.ae", "-")
            .put("yahoo.nl", "-")
            .put("yahoo.co.nz", "-")
            .put("yahoo.no", "-")
            .put("yahoo.com.ph", "-")
            .put("yahoo.pl", "-")
            .put("yahoo.pt", "-")
            .put("yahoo.ro", "-")
            .put("yahoo.ru", "-")
            .put("yahoo.com.sg", "-")
            .put("yahoo.co.za", "-")
            .put("yahoo.es", "-")
            .put("yahoo.se", "-")
            .put("yahoo.ch/fr", "-")
            .put("yahoo.ch/de", "-")
            .put("yahoo.com.tw", "-")
            .put("yahoo.co.th", "-")
            .put("yahoo.com.tr", "-")
            .put("yahoo.co.uk", "-")
            .put("yahoo.com", "-")
            .put("yahoo.com.vn", "-")
            .build();

    public static String normalize(String email) {
        return normalize(email, DEFAULT_OPTIONS);
    }

    public static String normalize(String email, Options options){

        email = email.trim().toLowerCase();

        String[] emailParts = email.split("@");
        String user = emailParts[0];
        String domain = emailParts[1];

        if(options.isForceRemoveTags()){
            user = user.replaceAll("[-+=].*", "");
        } else {
            String separator = Email.domainsWithTag.get(domain);
            if(separator != null){
                user = user.split(separator)[0];
            }
        }

        if(options.isForceRemoveDots() || Email.MATCH_GMAIL_PATTERN.matcher(domain).matches()){
            user = user.replace(".", "");
        }

        if("googlemail.com".equals(domain)){
            domain = "gmail.com";
        }

        return user + "@" + domain;
    }
}
