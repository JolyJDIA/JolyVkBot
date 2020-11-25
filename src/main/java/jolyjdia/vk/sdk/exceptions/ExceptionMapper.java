package jolyjdia.vk.sdk.exceptions;

import jolyjdia.vk.sdk.objects.base.Error;

public final class ExceptionMapper {
    private ExceptionMapper() {}

    public static ApiException parseException(Error error) {
        switch (error.getErrorCode()) {
            case 1:
                return new ApiException(1, "Unknown error occurred", error.getErrorMsg());
            case 2:
                return new ApiException(2, "Application is disabled. Enable your application or use test mode", error.getErrorMsg());
            case 3:
                return new ApiException(3, "Unknown method passed", error.getErrorMsg());
            case 4:
                return new ApiException(4, "Incorrect signature", error.getErrorMsg());
            case 260:
                return new ApiException(260, "Access to the groups list is denied due to the user's privacy settings", error.getErrorMsg());
            case 5:
                return new ApiException(5, "User authorization failed", error.getErrorMsg());
            case 6:
                return new ApiException(6, "Too many requests per second", error.getErrorMsg());
            case 7:
                return new ApiException(7, "Permission to perform this action is denied", error.getErrorMsg());
            case 8:
                return new ApiException(8, "Invalid request", error.getErrorMsg());
            case 9:
                return new ApiException(9, "Flood control", error.getErrorMsg());
            case 10:
                return new ApiException(10, "Internal server error", error.getErrorMsg());
            case 11:
                return new ApiException(11, "In test mode application should be disabled or user should be authorized", error.getErrorMsg());
            case 12:
                return new ApiException(12, "Unable to compile code", error.getErrorMsg());
            case 13:
                return new ApiException(13, "Runtime error occurred during code invocation", error.getErrorMsg());
            case 14:
                return new ApiException(14, "Captcha needed", error.getErrorMsg());
            case 15:
                return new ApiException(15, "Access denied", error.getErrorMsg());
            case 16:
                return new ApiException(16, "HTTP authorization failed", error.getErrorMsg());
            case 17:
                return new ApiException(17, "Validation required", error.getErrorMsg());
            case 18:
                return new ApiException(18, "User was deleted or banned", error.getErrorMsg());
            case 19:
                return new ApiException(19, "Content blocked", error.getErrorMsg());
            case 20:
                return new ApiException(20, "Permission to perform this action is denied for non-standalone applications", error.getErrorMsg());
            case 21:
                return new ApiException(21, "Permission to perform this action is allowed only for standalone and OpenAPI applications", error.getErrorMsg());
            case 22:
                return new ApiException(22, "Upload error", error.getErrorMsg());
            case 23:
                return new ApiException(23, "This method was disabled", error.getErrorMsg());
            case 24:
                return new ApiException(24, "Confirmation required", error.getErrorMsg());
            case 25:
                return new ApiException(25, "Token confirmation required", error.getErrorMsg());
            case 27:
                return new ApiException(27, "Group authorization failed", error.getErrorMsg());
            case 28:
                return new ApiException(28, "Application authorization failed", error.getErrorMsg());
            case 29:
                return new ApiException(29, "Rate limit reached", error.getErrorMsg());
            case 30:
                return new ApiException(30, "This profile is private", error.getErrorMsg());
            case 1310:
                return new ApiException(1310, "Catalog is not available for this user", error.getErrorMsg());
            case 1311:
                return new ApiException(1311, "Catalog categories are not available for this user", error.getErrorMsg());
            case 800:
                return new ApiException(800, "This video is already added", error.getErrorMsg());
            case 801:
                return new ApiException(801, "Comments for this video are closed", error.getErrorMsg());
            case 300:
                return new ApiException(300, "This album is full", error.getErrorMsg());
            case 302:
                return new ApiException(302, "Albums number limit is reached", error.getErrorMsg());
            case 1600:
                return new ApiException(1600, "Story has already expired", error.getErrorMsg());
            case 1602:
                return new ApiException(1602, "Incorrect reply privacy", error.getErrorMsg());
            case 1105:
                return new ApiException(1105, "Too many auth attempts, try again later", error.getErrorMsg());
            case 600:
                return new ApiException(600, "Permission denied. You have no access to operations specified with given object(s)", error.getErrorMsg());
            case 1112:
                return new ApiException(1112, "Processing.. Try later", error.getErrorMsg());
            case 601:
                return new ApiException(601, "Permission denied. You have requested too many actions this day. Try later.", error.getErrorMsg());
            case 602:
                return new ApiException(602, "Some part of the request has not been completed", error.getErrorMsg());
            case 603:
                return new ApiException(603, "Some ads error occured", error.getErrorMsg());
            case 100:
                return new ApiException(100, "One of the parameters specified was missing or invalid", error.getErrorMsg());
            case 101:
                return new ApiException(101, "Invalid application API ID", error.getErrorMsg());
            case 103:
                return new ApiException(103, "Out of limits", error.getErrorMsg());
            case 104:
                return new ApiException(104, "Not found", error.getErrorMsg());
            case 105:
                return new ApiException(105, "Couldn't save file", error.getErrorMsg());
            case 106:
                return new ApiException(106, "Unable to process action", error.getErrorMsg());
            case 1900:
                return new ApiException(1900, "Card not found", error.getErrorMsg());
            case 1901:
                return new ApiException(1901, "Too many cards", error.getErrorMsg());
            case 1902:
                return new ApiException(1902, "Card is connected to post", error.getErrorMsg());
            case 113:
                return new ApiException(113, "Invalid user id", error.getErrorMsg());
            case 114:
                return new ApiException(114, "Invalid album id", error.getErrorMsg());
            case 629:
                return new ApiException(629, "Object deleted", error.getErrorMsg());
            case 118:
                return new ApiException(118, "Invalid server", error.getErrorMsg());
            case 119:
                return new ApiException(119, "Invalid title", error.getErrorMsg());
            case 1400:
                return new ApiException(1400, "Too late for restore", error.getErrorMsg());
            case 1401:
                return new ApiException(1401, "Comments for this market are closed", error.getErrorMsg());
            case 121:
                return new ApiException(121, "Invalid hash", error.getErrorMsg());
            case 1402:
                return new ApiException(1402, "Album not found", error.getErrorMsg());
            case 122:
                return new ApiException(122, "Invalid photos", error.getErrorMsg());
            case 1403:
                return new ApiException(1403, "Item not found", error.getErrorMsg());
            case 1404:
                return new ApiException(1404, "Item already added to album", error.getErrorMsg());
            case 125:
                return new ApiException(125, "Invalid group id", error.getErrorMsg());
            case 1405:
                return new ApiException(1405, "Too many items", error.getErrorMsg());
            case 1150:
                return new ApiException(1150, "Invalid document id", error.getErrorMsg());
            case 1406:
                return new ApiException(1406, "Too many items in album", error.getErrorMsg());
            case 1151:
                return new ApiException(1151, "Access to document deleting is denied", error.getErrorMsg());
            case 1407:
                return new ApiException(1407, "Too many albums", error.getErrorMsg());
            case 1152:
                return new ApiException(1152, "Invalid document title", error.getErrorMsg());
            case 1408:
                return new ApiException(1408, "Item has bad links in description", error.getErrorMsg());
            case 1153:
                return new ApiException(1153, "Access to document is denied", error.getErrorMsg());
            case 129:
                return new ApiException(129, "Invalid photo", error.getErrorMsg());
            case 900:
                return new ApiException(900, "Can't send messages for users from blacklist", error.getErrorMsg());
            case 901:
                return new ApiException(901, "Can't send messages for users without permission", error.getErrorMsg());
            case 902:
                return new ApiException(902, "Can't send messages to this user due to their privacy settings", error.getErrorMsg());
            case 1160:
                return new ApiException(1160, "Original photo was changed", error.getErrorMsg());
            case 907:
                return new ApiException(907, "Value of ts or pts is too old", error.getErrorMsg());
            case 908:
                return new ApiException(908, "Value of ts or pts is too new", error.getErrorMsg());
            case 140:
                return new ApiException(140, "Page not found", error.getErrorMsg());
            case 909:
                return new ApiException(909, "Can't edit this message, because it's too old", error.getErrorMsg());
            case 141:
                return new ApiException(141, "Access to page denied", error.getErrorMsg());
            case 910:
                return new ApiException(910, "Can't sent this message, because it's too big", error.getErrorMsg());
            case 911:
                return new ApiException(911, "Keyboard format is invalid", error.getErrorMsg());
            case 912:
                return new ApiException(912, "This is a chat bot feature, change this status in settings", error.getErrorMsg());
            case 913:
                return new ApiException(913, "Too many forwarded messages", error.getErrorMsg());
            case 914:
                return new ApiException(914, "Message is too long", error.getErrorMsg());
            case 1170:
                return new ApiException(1170, "Too many feed lists", error.getErrorMsg());
            case 146:
                return new ApiException(146, "The mobile number of the user is unknown", error.getErrorMsg());
            case 147:
                return new ApiException(147, "Application has insufficient funds", error.getErrorMsg());
            case 148:
                return new ApiException(148, "Access to the menu of the user denied", error.getErrorMsg());
            case 917:
                return new ApiException(917, "You don't have access to this chat", error.getErrorMsg());
            case 150:
                return new ApiException(150, "Invalid timestamp", error.getErrorMsg());
            case 919:
                return new ApiException(919, "You can't see invite link for this chat", error.getErrorMsg());
            case 920:
                return new ApiException(920, "Can't edit this kind of message", error.getErrorMsg());
            case 921:
                return new ApiException(921, "Can't forward these messages", error.getErrorMsg());
            case 924:
                return new ApiException(924, "Can't delete this message for everybody", error.getErrorMsg());
            case 925:
                return new ApiException(925, "You are not admin of this chat", error.getErrorMsg());
            case 927:
                return new ApiException(927, "Chat does not exist", error.getErrorMsg());
            case 931:
                return new ApiException(931, "You can't change invite link for this chat", error.getErrorMsg());
            case 932:
                return new ApiException(932, "Your community can't interact with this peer", error.getErrorMsg());
            case 935:
                return new ApiException(935, "User not found in chat", error.getErrorMsg());
            case 936:
                return new ApiException(936, "Contact not found", error.getErrorMsg());
            case 171:
                return new ApiException(171, "Invalid list id", error.getErrorMsg());
            case 939:
                return new ApiException(939, "Message request already sent", error.getErrorMsg());
            case 940:
                return new ApiException(940, "Too many posts in messages", error.getErrorMsg());
            case 173:
                return new ApiException(173, "Reached the maximum number of lists", error.getErrorMsg());
            case 174:
                return new ApiException(174, "Cannot add user himself as friend", error.getErrorMsg());
            case 942:
                return new ApiException(942, "Cannot pin one-time story", error.getErrorMsg());
            case 175:
                return new ApiException(175, "Cannot add this user to friends as they have put you on their blacklist", error.getErrorMsg());
            case 176:
                return new ApiException(176, "Cannot add this user to friends as you put him on blacklist", error.getErrorMsg());
            case 177:
                return new ApiException(177, "Cannot add this user to friends as user not found", error.getErrorMsg());
            case 180:
                return new ApiException(180, "Note not found", error.getErrorMsg());
            case 181:
                return new ApiException(181, "Access to note denied", error.getErrorMsg());
            case 182:
                return new ApiException(182, "You can't comment this note", error.getErrorMsg());
            case 183:
                return new ApiException(183, "Access to comment denied", error.getErrorMsg());
            case 700:
                return new ApiException(700, "Cannot edit creator role", error.getErrorMsg());
            case 701:
                return new ApiException(701, "User should be in club", error.getErrorMsg());
            case 702:
                return new ApiException(702, "Too many officers in club", error.getErrorMsg());
            case 703:
                return new ApiException(703, "You need to enable 2FA for this action", error.getErrorMsg());
            case 704:
                return new ApiException(704, "User needs to enable 2FA for this action", error.getErrorMsg());
            case 706:
                return new ApiException(706, "Too many addresses in club", error.getErrorMsg());
            case 711:
                return new ApiException(711, "Application is not installed in community", error.getErrorMsg());
            case 200:
                return new ApiException(200, "Access to the album is prohibited", error.getErrorMsg());
            case 201:
                return new ApiException(201, "Access to audio is prohibited", error.getErrorMsg());
            case 203:
                return new ApiException(203, "Access to the group is denied", error.getErrorMsg());
            case 204:
                return new ApiException(204, "Access to the video is prohibited", error.getErrorMsg());
            case 205:
                return new ApiException(205, "Access to the market is prohibited", error.getErrorMsg());
            case 2000:
                return new ApiException(2000, "Servers number limit is reached", error.getErrorMsg());
            case 210:
                return new ApiException(210, "Access to wall's post denied", error.getErrorMsg());
            case 211:
                return new ApiException(211, "Access to wall's comment denied", error.getErrorMsg());
            case 212:
                return new ApiException(212, "Access to post comments denied", error.getErrorMsg());
            case 213:
                return new ApiException(213, "Access to status replies denied", error.getErrorMsg());
            case 214:
                return new ApiException(214, "Access to adding post denied", error.getErrorMsg());
            case 219:
                return new ApiException(219, "Advertisement post was recently added", error.getErrorMsg());
            case 220:
                return new ApiException(220, "Too many recipients", error.getErrorMsg());
            case 221:
                return new ApiException(221, "User disabled track name broadcast", error.getErrorMsg());
            case 222:
                return new ApiException(222, "Hyperlinks are forbidden", error.getErrorMsg());
            case 223:
                return new ApiException(223, "Too many replies", error.getErrorMsg());
            case 224:
                return new ApiException(224, "Too many ads posts", error.getErrorMsg());
            case 1251:
                return new ApiException(1251, "This achievement is already unlocked", error.getErrorMsg());
            case 1000:
                return new ApiException(1000, "Invalid phone number", error.getErrorMsg());
            case 1256:
                return new ApiException(1256, "Subscription not found", error.getErrorMsg());
            case 1257:
                return new ApiException(1257, "Subscription is in invalid status", error.getErrorMsg());
            case 1260:
                return new ApiException(1260, "Invalid screen name", error.getErrorMsg());
            case 1004:
                return new ApiException(1004, "This phone number is used by another user", error.getErrorMsg());
            case 500:
                return new ApiException(500, "Permission denied. You must enable votes processing in application settings", error.getErrorMsg());
            case 503:
                return new ApiException(503, "Not enough votes", error.getErrorMsg());
            case 250:
                return new ApiException(250, "Access to poll denied", error.getErrorMsg());
            case 251:
                return new ApiException(251, "Invalid poll id", error.getErrorMsg());
            case 252:
                return new ApiException(252, "Invalid answer id", error.getErrorMsg());
            case 253:
                return new ApiException(253, "Access denied, please vote first", error.getErrorMsg());
            default:
                return new ApiException(error.getErrorCode(), error.getErrorMsg());
        }
    }
}
