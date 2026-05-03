package utils;

import java.util.regex.Pattern;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;

public class Constants {
    static String configFilePath = System.getProperty("config.file");
    static Config config = configFilePath != null ? ConfigFactory.parseFile(new File(configFilePath)) : ConfigFactory.load();

    public static final String URL_LOCAL = "http://localhost";
    public static final String URL_HOST = "http://localhost";
    public static final String URL_SERVER = "http://opennex.org";
    public static final String URL_SERVER_HAWKING = "http://opennex.org";

    public static final String AWS_FILE_NAME_PREFIX = config.getString("aws.fileNamePrefix");

    public static final int OPENNEX_PROJECT_ZONE_ID = 0;
    public static final int FROM_PRIVATE_ZONE = 2;

    public static final boolean CALLER_IS_MY_SPACE_PAGE = true;
    public static final boolean CALLER_IS_NOT_MY_SPACE_PAGE = false;

    public static final String NASA_APPLICATION_APPROVED = "approved";
    public static final String NASA_APPLICATION_PENDING = "pending";
    public static final String NASA_APPLICATION_REJECTED = "rejected";

    public static final long NASA_PENDING = 0;
    public static final long NASA_APPROVED = 1;
    public static final long NASA_REJECTED = -1;
    // API Call format
    public static final String FORMAT = "json";

    // reCaptcha
    public static final String RECAPTCHA_VALIDATE = "https://www.google.com/recaptcha/api/siteverify";

    public static final String PATTERN_RULES =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@#!%*?&])[A-Za-z\\d$@$!%*?&]{8,32}";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // pagination options
    public static final String PAGINATION_NUMBER_ITEM_TWENTY = "20";

    // http://www.freeformatter.com/java-dotnet-escape.html -- this is for escape text purpose
    // html head

    public static final String htmlHead1 = "<head>\r\n    <meta charset=\"utf-8\">\r\n    " +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n    " +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n    " +
            "<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* " +
            "these tags -->\r\n    <title>Climate Service</title>\r\n    \r\n    " +
            "<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-2.1.4.min.js\"></script>\r\n    " +
            "<script type=\"text/javascript\" src=\"/assets/javascripts/parameter.js\"></script>\r\n\r\n   ";
    public static final String htmlHead2 = " </script><!-- Bootstrap -->\r\n    " +
            "<link href=\"/assets/stylesheets/bootstrap.min.css\" rel=\"stylesheet\">\r\n\r\n    " +
            "<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\r\n    " +
            "<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n    " +
            "<!--[if lt IE 9]>\r\n    <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\">" +
            "</script>\r\n    <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n    " +
            "<![endif]-->\r\n</head>\r\n<body>\r\n\r\n<h2 class=\"text-center\">";
    public static final String htmlHead3 = "</h2>\r\n\r\n<p class=\"text-center col-md-8 col-md-offset-2\">";
    public static final String htmlHead4 = "</p>\r\n\r\n<div class=\"container col-md-6\">\r\n    <form>\r\n        " +
            "<table class=\"table table-bordered table-striped\">\r\n            <thead>\r\n            <tr>\r\n  " +
            "              <th class=\"col-md-2\">Parameter Name</th>\r\n              " +
            "  <th class=\"col-md-4\">Value</th>\r\n            </tr>\r\n            </thead>\r\n         " +
            "   <tbody id=\"dynamicTBody\">";
    public static final String putVar = "<script>var varList = {\r\n\"pr\":       [\"Precipitation Flux\",                                \"\", 2, \"\"],    \r\n\"clt\":      [\"Total Cloud Fraction\",                              \"\", 2, \"\"],      \r\n\"ts\":       [\"Surface Temperature\",                               \"\", 2, \"\"],     \r\n\"lst_day\":  [\"Daytime Land Surface Temperature\",                  \"\", 2, \"\"],   \r\n\"lst_night\":[\"Nighttime Land Surface Temperature\",                \"\", 2, \"\"],   \r\n\"tas\":      [\"Near-Surface Air Temperature\",                      \"\", 2, \"\"],   \r\n\"hurs\":     [\"Near-Surface Relative Humidity\",                    \"\", 2, \"\"],   \r\n\"tos\":      [\"Sea Surface Temperature\",                           \"\", 2, \"\"],         \r\n\"uas\":      [\"Eastward Near-Surface Wind\",                        \"\", 2, \"\"],            \r\n\"vas\":      [\"Northward Near-Surface Wind\",                       \"\", 2, \"\"],             \r\n\"sfcWind\":  [\"Near-Surface Wind Speed\",                           \"\", 2, \"\"],         \r\n\"zos\":      [\"Sea Surface Height\",                                \"\", 2, \"\"],    \r\n\"lai\":      [\"Leaf Area Index\",                                   \"\", 2, \"\"], \r\n\"zl\":       [\"Equivalent Water Height Over Land\",                 \"\", 2, \"\"],                   \r\n\"zo\":       [\"Equivalent Water Height Over Ocean\",                \"\", 2, \"\"],                    \r\n\"ohc700\":   [\"Ocean Heat Content Anomaly within 700 m Depth\",     \"\", 2, \"\"],                \r\n\"ohc2000\":  [\"Ocean Heat Content Anomaly within 2000 m Depth\",    \"\", 2, \"\"],                \r\n\"rlds\":     [\"Surface Downwelling Longwave Radiation\",            \"\", 2, \"\"],                        \r\n\"rsds\":     [\"Surface Downwelling Shortwave Radiation\",           \"\", 2, \"\"],                         \r\n\"rlus\":     [\"Surface Upwelling Longwave Radiation\",              \"\", 2, \"\"],                      \r\n\"rsus\":     [\"Surface Upwelling Shortwave Radiation\",             \"\", 2, \"\"],                       \r\n\"rldscs\":   [\"Surface Downwelling Clear-Sky Longwave Radiation\",  \"\", 2, \"\"],             \r\n\"rsdscs\":   [\"Surface Downwelling Clear-Sky Shortwave Radiation\", \"\", 2, \"\"],                   \r\n\"rsuscs\":   [\"Surface Upwelling Clear-Sky Shortwave Radiation\",   \"\", 2, \"\"],                \r\n\"rsdt\":     [\"TOA Incident Shortwave Radiation\",                  \"\", 2, \"\"],                  \r\n\"rlut\":     [\"TOA Outgoing Longwave Radiation\",                   \"\", 2, \"\"],                 \r\n\"rsut\":     [\"TOA Outgoing Shortwave Radiation\",                  \"\", 2, \"\"],                  \r\n\"rlutcs\":   [\"TOA Outgoing Clear-Sky Longwave Radiation\",         \"\", 2, \"\"],       \r\n\"rsutcs\":   [\"TOA Outgoing Clear-Sky Shortwave Radiation\",        \"\", 2, \"\"],              \r\n\"ta\":       [\"Air Temperature\",                                   \"\", 3, \"\"], \r\n\"hus\":      [\"Specific Humidity\",                                 \"\", 3, \"\"],   \r\n\"cli\":      [\"Cloud Ice Water Content\",                           \"\", 3, \"\"],         \r\n\"clw\":      [\"Cloud Liquid Water Content\",                        \"\", 3, \"\"],            \r\n\"ot\":       [\"Ocean Temperature\",                            \"ocean\", 3, \"\"],   \r\n\"os\":       [\"Ocean Salinity\",                               \"ocean\", 3, \"\"],\r\n\"wap\":      [\"Vertical Wind Velocity\",                            \"\", 3, \"\"],        \r\n\"hur\":      [\"Relative Humidity\",                                 \"\", 3, \"\"] \r\n}\r\n;function put_var_disable(thisID, thatID, rule) {\r\n\tvar thisStr =  document.getElementById(thisID).value;\r\n\tvar thatInputTobe = rule[thisStr][1][0];  \r\n\r\n\tif (thatInputTobe == \"true\") {\r\n\t\tdocument.getElementById(thatID).disabled = false;\r\n\t}else {\r\n\t\tdocument.getElementById(thatID).disabled = true;\r\n\t}\r\n}\r\n;function put_var_input(thisID, thatID, rule) {\r\n\r\n\tvar thisStr =  document.getElementById(thisID).value;\r\n\tvar thatInputTobe = rule[thisStr][1][0];  \r\n\tconsole.log(thatInputTobe);\r\n\tdocument.getElementById(thatID).placeholder = thatInputTobe;\r\n\r\n}\r\n;function put_var(thisID, thatID, rule) {\r\n\tvar thatList=document.getElementById(thatID);\r\n\t\r\n\tfor (var i=thatList.length-1; i>=0; i--) {\r\n\t  \tthatList.remove(i);\r\n\t} \r\n\r\n\tvar thisStr =  document.getElementById(thisID).value;\r\n\tvar thatListTobe = rule[thisStr][1];  \r\n\tfor (var i=0; i<thatListTobe.length; i++) {\r\n\t  \tvar k = thatListTobe[i];\r\n\t  \tthatList.add(new Option(varList[k][0],k));\r\n\t  \t\r\n\t}\r\n}\r\n;";

    // html tail
    public static final String htmlTail1 = "</tbody>\r\n        </table>\r\n    </form>\r\n    " +
            "<div class=\"text-center\">\r\n    \t<button type=\"submit\" class=\"btn btn-success btn-lg\" " +
            "onclick=\"Javascript:sendValues('";
    public static final String htmlTail2 = "')\">Request Service</button>\r\n    " +
            "</div>\r\n</div>\r\n\r\n<div class=\"container col-md-6\">\r\n    <form>\r\n        " +
            "<table class=\"table table-bordered table-striped\">\r\n            <thead>\r\n            <tr>\r\n                <th>Output</th>\r\n            </tr>\r\n            </thead>\r\n            <tbody>\r\n            <tr>\r\n                <td>\r\n                    <a id=\"serviceImgLink\" href=\"\">\r\n                        <img src=\"\" id=\"serviceImg\" class=\"img-responsive\">\r\n                    </a>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <a id=\"commentLink\" href=\"\">\r\n                        <textarea class=\"form-control\" rows=\"3\" id=\"comment\"></textarea>\r\n                    </a>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <textarea class=\"form-control\" rows=\"10\" id=\"message\"></textarea>\r\n                </td>\r\n            </tr>\r\n            </tbody>\r\n        </table>\r\n        <div class=\"text-center\">\r\n            <button id = \"downloadButton\" type=\"button\" class=\"btn btn-success btn-lg\">Download Data</button>\r\n </div> <br> <div class=\"text-center\" id=\"output\">";
    public static final String htmlTail3 = "</div></form>\r\n</div>\r\n\r\n\r\n</body>\r\n</html>";
    //New service execution log stuff
    public static final String SERVICE_EXECUTION_LOG = "/serviceExecutionLog";
    public static final String SERVICE_EXECUTION_LOG_QUERY = "/queryServiceExecutionLogs";
    public static final String SERVICE_EXECUTION_LOG_GET = "/getServiceExecutionLog";
    public static final String NEW_GET_ALL_SERVICE_LOG = "/getAllServiceExecutionLog";


    //Static file paths
    public static final String GCMD_DATASET_PATH = "public/GCMDDataset/";
    /**********************************************	ports *************************************************************/
    public static final String CMU_BACKEND_PORT = ":9037";
    public static final String CMU_FRONTEND_PORT = ":9036";


    public static final String LOCAL_HOST_PORT = ":9032";
    /**********************************************	ports *************************************************************/

    /*================================================ Bug Reports =====================================================*/
    public static final String BUG_REPORT_REGISTER_POST = "/bugReport/addBugReport";
    public static final String GET_BUG_REPORT = "/bugReport/getBugReport/id/";
    public static final String BUG_REPORT_EDIT_POST = "/bugReport/updateBugReport/id/";
    public static final String DELETE_BUG_REPORT = "/bugReport/deleteBugReport/id/";
    public static final String UPDATE_BUG_SOLVED = "/bugReport/updateBugReportSolved/";
    public static final String GET_ALL_BUG_REPORTS = "/bugReport/getAllBugReports/json";
    /*================================================ Bug Reports =====================================================*/


    /*========================================== Backend API URLs ====================================================*/

    /*================================================ Analytics =====================================================*/
    public static final String GET_ALL_TAGS = "/analytics/getAllTags";

    public static final String GET_RELATIONAL_GRAPH = "/analytics/getRelationalKnowledgeGraph/json";
    public static final String GET_SHORTEST_PATH = "/graphAlgorithm/getShortestPath/source/";
    public static final String GET_KTH_SHORTEST_PATH = "/graphAlgorithm/getKthShortestPath/source/";


    //Service in Map
    public static final String SERVICE_LOCATION = "/user/getLocationService";

    //Dataset in Map
    public static final String DATASET_LOCATION = "/dataset/getDatasetByLocation";


    //user ip address
    public static final String ADD_ACCESS_IP = "/user/addAccessAddress";

    /*================================================ Analytics =====================================================*/



    /*=================================================== APIs =======================================================*/

    public static final String REGISTER_SITE_NAVIGATION_STRUCTURE =
            "/siteNavigationStructure/registerSiteNavigationStructure";
    public static final String SITE_NAVIGATION_STRUCTURE_LIST = "/siteNavigationStructure/siteNavigationStructureList";
    public static final String GET_SITE_NAVIGATION_SITE_BY_ID = "/siteNavigationStructure/getSiteNavigationStructure/";
    public static final String UPDATE_SITE_NAVIGATION_STRUCTURE =
            "/siteNavigationStructure/updateSiteNavigationStructure/";
    public static final String DELETE_SITE_NAVIGATION_SITE_BY_ID =
            "/siteNavigationStructure/deleteSiteNavigationStructure/";
    public static final String TOGGLE_SITE_NAVIGATION_STRUCTURE_VALID = "/siteNavigationStructure/toggleValid/";
    public static final String REGISTER_SITE_NAVIGATION_EVENT = "/siteNavigationEvent/registerSiteNavigationEvent/";
    //*****************************************End: Admin related endpoints********************************************/


    public static final String GET_ALL_EVENTS = "/service/getAllAPIEvents";


    public static final String GET_TOP_K_USED_SERVICES_BY_DATASET_ID_AND_K =
            "/service/getTopKUsedServicesByDatasetIdAndK";



    public static final String ASSOCIATE_PROJECT_TO_API = "/project/associateProjectsToAPI/";
    public static final String ASSOCIATE_PROJECT_TO_DOCKER = "/project/associateProjectsToDocker/";
    public static final String ASSOCIATE_PROJECT_TO_DATASET = "/project/associateProjectsToDataset/";


    /*=================================================== APIs =======================================================*/


    /*============================================= API Execution Logs ===============================================*/

    public static final String GET_EXECUTION_LOG_BY_ID = "/serviceExecutionLog/getServiceExecutionLog/";

    /*============================================= API Execution Logs =================================================*/


    /*============================================= Broadcast Messages =================================================*/
    public static final String GET_MSG = "/broadcastMsg/getAllMsg/";
    public static final String MARK_MSG_READ = "/broadcastMsg/markMsgsAsRead";
    public static final String GET_ALL_UNREAD_MSG_BY_USER = "/broadcastMsg/getUnReadMsgByUser/";
    public static final String DELETE_MSGS = "/broadcastMsg/deleteMsgs";
    public static final String APPROVE_MSGS = "/broadcastMsg/approveMsgs";
    public static final String REJECT_MSGS = "/broadcastMsg/rejectPendingMsgs";
    /*============================================= Broadcast Messages ===============================================*/


    /*=============================================== Broadcast Email ================================================*/
    public static final String SEND_BROADCAST_EMAIL = "/broadcast/email";
    /*=============================================== Broadcast Email ================================================*/



    /*================================================ Capabilities ==================================================*/


    // category
    public static final String GET_ALL_CATEGORIES = "/category";

    public static final String GET_SUPPORTAPP_BY_ID = "/supportApp/get/";
    public static final String GET_CAPABILITY_AUTHORS = "/supportApp/getSupportAppAuthorsByCid/";

    public static final String GET_COUNT_OF_STATUSES = "/supportApp/countByStatuses";
    public static final String GET_APPS_BY_IDS = "/supportApp/getAppsByIds/";


    /*================================================ Capabilities ==================================================*/


    /*==================================================== Comments ==================================================*/
    public static final String GET_REPLY_BY_COMMENT_ID = "/comment/getReplyByCommentId/";
    public static final String ADD_THUMB_UP = "/comment/addThumbUp/";
    public static final String ADD_THUMB_DOWN = "/comment/addThumbDown/";


    /*==================================================== Comments ==================================================*/



    public static final String GET_DOCKER_EXECUTIONLOG_BY_ID = "/dockerExecutionLog/getDockerExecutionLog/";

    /*================================================ Container Execution Logs ======================================*/

    /*================================================== Contracts ===================================================*/
    public static final String CREATE_CONTRACTS = "/contract";
    public static final String GET_CONTRACTS = "/contract";
    public static final String POST_CONTRACTS = "/contract/sign";
    /*================================================== Contracts ===================================================*/


    /*================================================= Datasets =====================================================*/
    public static final String DATASET_REGISTER_POST = "/dataset/datasetRegisterPOST";
    public static final String DATASET_FOLLOWED_BY_USER = "/dataset/follow";
    public static final String GET_DATASET_BY_ENTRY_ID = "/dataset/datasetDetail/";
    public static final String GET_DATASET_BY_ID = "/dataset/getDatasetById/";
    public static final String GET_PROJECTS_BY_DATASET_ID = "/dataset/getProjectsByDatasetId/";
    public static final String UPDATE_DATASET_BY_ID = "/dataset/updateDatasetById/";

    public static final String DATASET_SEARCH_POST = "/dataset/datasetSearchPost/";
    public static final String DATASET_ENTRY_LIST = "/dataset/datasetEntryList/";
    public static final String REGISTERED_DATASET = "/dataset/registeredDatasets";
    public static final String MY_FOLLOWED_DATASET = "/dataset/myFollowedDatasets";
    public static final String GET_MOST_K_POPULAR_DATASETS_CALL = "/dataset/getMostKPopularDatasets";
    public static final String GET_MY_FOLLOWED_DATASETS = "/dataset/getMyFollowedDatasets";
    public static final String DATASET_UNFOLLOWED_BY_USER = "/dataset/unfollow";
    public static final String GET_INSTRUMENT_NAME_LIST = "/dataset/getInstrumentNameList";
    public static final String GET_FOLLOWERS_FOR_DATASET = "/dataset/followers/";
    public static final String DATASET_COMMENTS = "/dataset/comment/";
    public static final String ADD_IMAGE_TO_DATASET = "/dataset/image/";
    public static final String REMOVE_DATASET_IMAGES = "/dataset/removeImages/";
    public static final String ADD_ALL_DATASETS = "/dataset/addAll";
    public static final String UPDATE_ALL_DATASETS = "/dataset/updateAll";
    public static final String ASSOCIATE_ALL_DATASETS = "/dataset/associateAll";
    public static final String DELETE_ALL_DATASETS = "/dataset/deleteAll";
    public static final String GET_ALL_DATASETS_BY_IDS = "/dataset/getAllByIds";
    public static final String ADD_DATASET_TAGS = "/dataset/addTags/";
    public static final String UPDATE_DATASET_RATING = "/dataset/updateRating/";
    public static final String ADD_DATASET_COMMENT = "/dataset/addComment";
    public static final String DELETE_DATASET = "/dataset/delete/";
    public static final String PROCESS_ALL_GCMD_DATASET = "/dataset/processAllGCMDDataset";


    // dataset instrument
    public static final String GET_DATASET_INSTRUMENTS = "/dataset/instrument/";
    public static final String QUERY_INSTRUMENT_BY_NAME = "/dataset/instrument/query?name=";
    /*================================================= Datasets =====================================================*/


    /*================================================ Dataset Logs ==================================================*/
    public static final String DELETE_DATASET_LOGS = "/datasetLog/deleteDatasetlog";
    /*================================================ Dataset Logs ==================================================*/




    /*==================================================== Mails =====================================================*/
    public static final String GET_INBOX = "/mails/received";
    public static final String GET_SENT = "/mails/sent";
    public static final String POST_MAIL = "/mails";
    public static final String GET_MAIL = "/mails/";
    /*==================================================== Mails =====================================================*/




    /*==================================================== News ======================================================*/
    public static final String GET_ALL_NEWS = "/news/getAllNews";
    public static final String NEWS_LIST = "/news/getNewsList";
    public static final String ADD_NEWS = "/news/addOneNews";
    public static final String GET_NEWS_BY_ID = "/news/getNewsById/";
    public static final String READ_NEWS = "/news/readNews";

    public static final String GET_NEWS_IMAGE_BY_ID = "/news/image/";
    public static final String SET_NEWS_IMAGE = "/news/setImage/";
    public static final String DELETE_NEWS_IMAGE = "/news/deleteImage/";
    public static final String UPDATE_NEWS = "/news/updateNews/";
    public static final String SAVE_NEWS_DESCRIPTION_IMG = "/news/saveDescriptionImage/";
    public static final String DELETE_NEWS_BY_ID = "/news/deleteNewsById/";
    public static final String STUDY_CASE_LIST = "/news/studyCaseList/";
    public static final String ASSOCIATE_ITEMS_TO_NEWS = "/news/associateItemsToNews/";
    /*==================================================== News ======================================================*/

    /*================================================= Notebooks ====================================================*/
    public static final String NOTEBOOK_REGISTER_POST = "/notebook/notebookRegisterPOST";
    public static final String SAVE_NOTEBOOK_IMAGE = "/notebook/addNotebookImage/";
    public static final String SAVE_NOTEBOOK_FILE = "/notebook/saveNotebookFile/";
    public static final String GET_NOTEBOOK_BY_ID = "/notebook/getNotebookById/";
    public static final String NOTEBOOK_EDIT_POST = "/notebook/updateNotebookById/";
    public static final String DELETE_NOTEBOOK_FILE = "/notebook/deleteNotebookFile/";
    public static final String DELETE_NOTEBOOK_IMAGE = "/notebook/deleteNotebookImage/";
    public static final String NOTEBOOK_LIST = "/notebook/notebookList/";
    public static final String GET_WISHES_BY_NOTEBOOK_ID = "/notebook/getWishesByNotebookId/";
    public static final String GET_PROJECTS_BY_NOTEBOOK_ID = "/notebook/getProjectsByNotebookId/";

    public static final String REGISTERED_NOTEBOOKS = "/notebook/registeredNotebooks";
    public static final String MY_FOLLOWED_NOTEBOOKS = "/notebook/myFollowedNotebooks";
    public static final String GET_ALL_FOLLOWED_EVENTS_NOTEBOOK = "/notebook/getAllFollowedEventsNotebook";
    public static final String NOTEBOOK_FOLLOWED_BY_USER = "/notebook/followedByUser";
    public static final String NOTEBOOK_UNFOLLOWED_BY_USER = "/notebook/unFollowedByUser";
    public static final String ASSOCIATE_PROJECT_TO_NOTEBOOK = "/project/associateProjectsToNotebook/";

    public static final String DELETE_NOTEBOOK = "/notebook/deleteNotebook/";
    public static final String ADD_COMMENT_FOR_NOTEBOOK = "/notebook/addComment";
    public static final String ADD_NOTEBOOK_TAGS = "/notebook/addTags/";
    public static final String UPDATE_NOTEBOOK_RATING = "/notebook/updateRating/";
    public static final String NOTEBOOK_GET_COMMENTS = "/notebook/getComments/";

    public static final String NOTEBOOK_SEARCH_POST = "/notebook/notebookSearchPost/";
    public static final String GET_MY_FOLLOWED_NOTEBOOKS = "/notebook/getMyFollowedNoteboks";

    public static final String ASSOCIATE_WISHES_TO_NOTEBOOK = "/notebook/associateWishesToNotebook/";
    public static final String APPROVE_NASA_ENDORSEMENT_APPLICATION_FOR_NOTEBOOK =
            "/notebook/approveNASAEndorsementApplication";
    public static final String REJECT_NASA_ENDORSEMENT_APPLICATION_FOR_NOTEBOOK =
            "/notebook/rejectNASAEndorsementApplication";
    /*================================================= Notebooks ====================================================*/

    /*=============================================== Notebook Execution Logs ========================================*/
    public static final String ADD_NOTEBOOK_EXECUTION_LOG = "/notebookExecutionLog/addNotebookExecutionLog";
    public static final String FIND_NOTEBOOK_LOGS_BY_USER = "/notebookExecutionLog/findNotebookLogsByUser/";
    public static final String FIND_NOTEBOOK_LOGS_BY_USER_POST = "/notebookExecutionLog/findNotebookLogsByUserPOST/";
    public static final String FIND_INTERESTED_NOTEBOOK_LOGS_BY_USER =
            "/notebookExecutionLog/findInterestedNotebookLogsByUser/";
    public static final String FIND_INTERESTED_FOLLOWED_NOTEBOOK_LOGS_BY_USER =
            "/notebookExecutionLog/findInterestedFollowedNotebookLogsByUser/";
    public static final String NOTEBOOK_LOG_INTERESTED_BY_USER = "/notebookExecutionLog/notebookLogInterestedByUser/";
    public static final String NOTEBOOK_LOG_NOT_INTERESTED_BY_USER =
            "/notebookExecutionLog/notebookLogNotInterestedByUser/";
    public static final String GET_NOTEBOOK_EXECUTIONLOG_BY_ID = "/notebookExecutionLog/getNotebookExecutionLog/";
    public static final String NOTEBOOK_EXECUTION_LOG_DETAIL = "/notebookExecutionLog/notebookExecutionLogDetail/";
    public static final String MAKE_PUBLIC_NOTEBOOK_LOGS = "/notebookExecutionLog/makeLogPublic";
    public static final String MAKE_PRIVATE_NOTEBOOK_LOGS = "/notebookExecutionLog/makeLogPrivate";

    /*=============================================== Notebook Execution Logs ========================================*/

    /*================================================= Notifications ================================================*/
    public static final String ADD_NOTIFICATION = "/notification/addMultipleNotifications";
    /*================================================= Notifications ================================================*/


    /*================================================= Challenge ====================================================*/
    public static final String CHALLENGE_REGISTER_POST = "/challenge/addChallenge";
    public static final String GET_CHALLENGE_BY_ID = "/challenge/challengeDetail/";
    public static final String CHALLENGE_EDIT_POST = "/challenge/updateChallenge/";
    public static final String CHALLENGE_EDIT_POST_ADMIN = "/challenge/updateChallengeAdmin/";
    public static final String CHALLENGE_APPLY_POST = "/challenge/applyChallenge/";

    public static final String CHALLENGE_PICTURE_POST = "/challenge/setImage/";
    public static final String CHALLENGE_PICTURE_GET = "/challenge/getImage/";
    public static final String CHALLENGE_PDF_POST = "/challenge/setPdf/";
//    public static final String DELETE_PROJECT_IMAGE = "/project/deleteProjectImage/";
//    public static final String DELETE_PROJECT_PDF = "/project/deleteProjectPDF/";
    public static final String CHALLENGE_LIST = "/challenge/challengeList/";
    public static final String CHALLENGE_LIST_ADMIN = "/challenge/challengeListAdmin/";
    public static final String CHALLENGE_APPLIED_BY_USER = "/challenge/getChallengesByApplicant/";
    public static final String CHALLENGE_POSTED_BY_USER = "/challenge/getChallengesByUser/";
    public static final String GET_CHALLENGE_APPLICATION_ID_BY_ID = "/challenge/challengeApplicationDetailId/";
    public static final String STR_BACKEND_URL_CHALLENGE_APPLICATIONS = "/challenge/getApplications/";
    public static final String GET_CHALLENGE_APPLICATION_BY_ID = "/challenge/challengeApplicationDetail/";


    public static final String CHALLENGE_APPLICATION_STATUS_UPDATE = "/challenge/updateChallengeApplicationStatus/";
    public static final String CHALLENGE_STATUS_UPDATE = "/challenge/updateStatus/";

//    public static final String GET_ALL_ACTIVE_PROJECTS = "/project/allproject";
//    public static final String DELETE_POPULAR_PROJECT = "/project/deletePopularProejct/";
//    public static final String GET_TEAM_MEMBERS_BY_PROJECT_ID = "/project/teammember/";
//    public static final String GET_PROJECTS_BY_CONDITION = "/project/search/conditions";
//    public static final String SET_PROJECT_IMAGE = "/project/setImage/";
//    public static final String SET_PROJECT_PDF = "/project/setPDF/";
//    public static final String ADD_TEAM_MEMBER = "/project/addTeamMember/";
//    public static final String SET_TEAM_MEMBER_PHOTO = "/project/setTeamMemberPhoto/";
//    public static final String DELETE_TEAM_MEMBER = "/project/deleteTeamMember/";
//    public static final String DELETE_PROJECT_BY_ID = "/project/deleteProject/";
//    public static final String GET_CHALLENGE_BY_CREATOR = "/challenge/getChallengeByUser/";
//    public static final String CHECK_PROJECT_NAME = "/project/isProjectNameExisted";
//
//    public static final String SAVE_PROJECT_DESCRIPTION_IMG = "/project/saveDescriptionImage/";
//    public static final String RENAME_PROJECT_DESCRIPTION_IMG = "/project/renameDescriptionImage/";
//    public static final String GET_MY_ENROLLED_PROJECTS = "/project/getMyEnrolledProjects";
//    public static final String ADD_ALL_PROJECTS = "/project/addAll";
//    public static final String DELETE_ALL_PROJECTS = "/project/deleteAll";
//    public static final String GET_PROJECT_ID_BY_NAME = "/project/getIdByName/";
//    public static final String PROJECT_FOLLOWED_BY_USER = "/project/followedByUser";
//    public static final String PROJECT_UNFOLLOWED_BY_USER = "/project/unFollowedByUser";
//    public static final String GET_ALL_FOLLOWED_EVENTS_PROJECT = "/project/getAllFollowedEventsProject";
//    public static final String GET_MY_FOLLOWED_PROJECTS = "/project/getMyFollowedProjects";
//    public static final String MY_FOLLOWED_PROJECTS = "/project/myFollowedProjects";
//    public static final String CHECK_PROJECT_EXIST = "/project/checkProjectExist/";
//    public static final String ADD_ONE_FOLLOWER_PROJECT = "/project/addOneFollower/";
//    public static final String DELETE_ONE_FOLLOWER_PROJECT = "/project/deleteOneFollower/";
//    public static final String GET_FOLLOWERS_FOR_PROJECT = "/project/followers/";
//    public static final String GET_ALL_POPULAR_PROJECTS = "/project/allPopularProjects";
//    public static final String ADD_POPULAR_PROJECT = "/project/addPopularProject";
//    public static final String REORDER_POPULAR_PROJECTS = "/project/reorderPopularProjects";

    /*================================================= Challenges =====================================================*/

    /*================================================= Projects =====================================================*/
    public static final String PROJECT_REGISTER_POST = "/project/addProject";
    public static final String GET_PROJECT_BY_ID = "/project/projectDetail/";
    public static final String PROJECT_EDIT_POST = "/project/updateProject/";
    public static final String DELETE_PROJECT_IMAGE = "/project/deleteProjectImage/";
    public static final String DELETE_PROJECT_PDF = "/project/deleteProjectPDF/";
    public static final String PROJECT_LIST = "/project/projectList/";

    public static final String FELLOWSHIP_LIST = "/project/fellowshipList/";

    public static final String GET_ALL_ACTIVE_PROJECTS = "/project/allproject";
    public static final String DELETE_POPULAR_PROJECT = "/project/deletePopularProejct/";
    public static final String GET_TEAM_MEMBERS_BY_PROJECT_ID = "/project/teammember/";
    public static final String GET_PROJECTS_BY_CONDITION = "/project/search/conditions";
    public static final String SET_PROJECT_IMAGE = "/project/setImage/";
    public static final String SET_PROJECT_PDF = "/project/setPDF/";
    public static final String ADD_TEAM_MEMBER = "/project/addTeamMember/";
    public static final String SET_TEAM_MEMBER_PHOTO = "/project/setTeamMemberPhoto/";
    public static final String DELETE_TEAM_MEMBER = "/project/deleteTeamMember/";
    public static final String DELETE_PROJECT_BY_ID = "/project/deleteProject/";
    public static final String GET_PROJECTS_BY_CREATOR = "/project/getProjectsByUser/";
    public static final String CHECK_PROJECT_NAME = "/project/isProjectNameExisted";

    public static final String SAVE_PROJECT_DESCRIPTION_IMG = "/project/saveDescriptionImage/";
    public static final String RENAME_PROJECT_DESCRIPTION_IMG = "/project/renameDescriptionImage/";
    public static final String GET_MY_ENROLLED_PROJECTS = "/project/getMyEnrolledProjects";
    public static final String ADD_ALL_PROJECTS = "/project/addAll";
    public static final String DELETE_ALL_PROJECTS = "/project/deleteAll";
    public static final String GET_PROJECT_ID_BY_NAME = "/project/getIdByName/";
    public static final String PROJECT_FOLLOWED_BY_USER = "/project/followedByUser";
    public static final String PROJECT_UNFOLLOWED_BY_USER = "/project/unFollowedByUser";
    public static final String GET_ALL_FOLLOWED_EVENTS_PROJECT = "/project/getAllFollowedEventsProject";
    public static final String GET_MY_FOLLOWED_PROJECTS = "/project/getMyFollowedProjects";
    public static final String MY_FOLLOWED_PROJECTS = "/project/myFollowedProjects";
    public static final String CHECK_PROJECT_EXIST = "/project/checkProjectExist/";
    public static final String ADD_ONE_FOLLOWER_PROJECT = "/project/addOneFollower/";
    public static final String DELETE_ONE_FOLLOWER_PROJECT = "/project/deleteOneFollower/";
    public static final String GET_FOLLOWERS_FOR_PROJECT = "/project/followers/";
    public static final String GET_ALL_POPULAR_PROJECTS = "/project/allPopularProjects";
    public static final String ADD_POPULAR_PROJECT = "/project/addPopularProject";
    public static final String REORDER_POPULAR_PROJECTS = "/project/reorderPopularProjects";
    public static final String CLOSE_CHALLENGE_BY_ID = "/challenge/closeChallenge/";

    /*================================================= Projects =====================================================*/

    /*=============================================== Organization ===================================================*/
    public static final String ORGANIZATION_REGISTER_POST = "/organization/register";
    public static final String ORGANIZATION_DETAIL = "/organization/organizationDetail/";
    public static final String ORGANIZATION_EDIT_POST = "/organization/organizationUpdate";
    public static final String ADD_USERS_ORGANIZATION = "/organization/addUsers";
    public static final String ORGANIZATION_LIST = "/organization/organizationList";

    public static final String ORGANIZATION_LIST_PAGE = "/organization/organizationListPage";
    public static final String ORGANIZATION_LIST_BY_USER = "/organization/organizationListByUser/";
    public static final String ORGANIZATION_LIST_BY_NAME = "/organization/organizationListByName";
    /*=============================================== Organization ===================================================*/


    /*================================================= Suggestions ==================================================*/
    public static final String SUGGESTION_REGISTER_POST = "/suggestion/addSuggestion";
    public static final String UPDATE_SUGGESTION_SOLVE = "/suggestion/updateSuggestionSolve/";
    public static final String SUGGESTION_LIST = "/suggestion/suggestionList/json";
    public static final String DELETE_ONE_SUGGESTION = "/suggestion/deleteSuggestion/id/";
    public static final String UPDATE_SUGGESTION = "/suggestion/updateSuggestion/id/";
    public static final String GET_SUGGESTION_BY_ID = "/suggestion/getSuggestion/id/";
    /*================================================= Suggestions ==================================================*/


    /*================================================= Users ========================================================*/
    public static final String USER_REGISTER_POST = "/user/addUser";
    public static final String USER_RESET_PASSWORD = "/user/resetPassword";
    public static final String USER_UPDATE_PASSWORD = "/user/updatePassword";
    public static final String USER_DETAIL = "/user/userDetail/";
    public static final String RESEARCHER_DETAIL = "/user/getResearcherInfo/";
    public static final String STUDENT_DETAIL = "/user/getStudentInfo/";
    public static final String USER_EDIT_POST = "/user/updateUser";
    public static final String USER_LOGIN = "/user/userLogin";
    public static final String CHECK_EMAIL = "/user/checkEmail";
    public static final String USER_LIST = "/user/userList";
    public static final String MY_FOLLOWEES = "/user/myFollowees";
    public static final String EMAIL_VALIDATE = "/user/validateEmail";

    public static final String GET_USER_PROFILE_BY_ID = "/user/userDetail/";


    public static final String GET_USER_IMAGE_BY_USER_ID = "/user/getUserImageByUserId/";
    // This is for front-end image path
    public static final String USER_IMAGE_BY_IMAGE_ID_PATH = "/user/userImageByImageId/";
    // This is for backend routing
    public static final String GET_USER_IMAGE_BY_IMAGE_ID = "/user/userImageByImageId/";
    public static final String GET_USER_BY_CONDITION = "/user/search/conditions";
    public static final String USER_DELETE = "/user/userDelete";



    public static final String USER_REGISTER_AUTO = "/user/autoRegisterUser";


    public static final String MY_FOLLOWERS = "/user/myFollowers";
    public static final String GET_FOLLOWEES_BY_USER_ID = "/user/getFolloweesByUserId";
    public static final String GET_ALL_USERS = "/user/getAllUsers";

    public static final String USER_DELETE_IMAGE = "/user/deleteImageForUser/";
    public static final String USER_UPDATE_IMAGE = "/user/updateImageForUser/";




    public static final String USER_FOLLOWED_BY_USER = "/user/followedByUser";
    public static final String USER_UNFOLLOWED_BY_USER = "/user/unFollowedByUser";

    public static final String GET_MY_FOLLOWED_USERS = "/user/getMyFollowers";
    public static final String SEND_PASSWORD_EMAIL = "/user/sendPasswordEmail";
    public static final String UPDATE_PASSWORD = "/user/updatePassword";

    public static final String ADD_ALL_USERS = "/user/addAll";


    /*================================================= Users ========================================================*/

    /*================================================= Technologies ==================================================*/
    public static final String TECHNOLOGY_REGISTER_POST = "/technology/addTechnology";
    public static final String GET_TECHNOLOGY_BY_ID = "/technology/technologyDetail/";

    public static final String TECHNOLOGY_REGISTER_FILE_POST = "/technology/setFiles/";

    public static final String DOWNLOAD_TECHNOLOGY_FILE = "/technology/fileDetail/";

        public static final String TECHNOLOGY_EDIT_POST = "/technology/updateTechnology/";
    public static final String DELETE_TECHNOLOGY_IMAGE = "/technology/deleteTechnologyImage/";
    public static final String DELETE_TECHNOLOGY_PDF = "/technology/deleteTechnologyPDF/";
    public static final String TECHNOLOGY_LIST = "/technology/technologyList/";

    public static final String GET_ALL_ACTIVE_TECHNOLOGY = "/technology/allproject";
    public static final String GET_TECHNOLOGIES_BY_CONDITION = "/technology/search/conditions";
    public static final String SET_TECHNOLOGY_PDF = "/technology/setPDF/";
    public static final String DELETE_TECHNOLOGY_BY_ID = "/technology/deleteTechnology/";
    public static final String GET_TECHNOLOGY_BY_PUBLISHER = "/technology/getTechnologyByUser/";
    public static final String CHECK_TECHNOLOGY_NAME = "/technology/isTechnologyNameExisted";

    public static final String SAVE_TECHNOLOGY_DESCRIPTION_IMG = "/project/saveDescriptionImage/";
    public static final String GET_MY_ENROLLED_TECHNOLOGY = "/project/getMyEnrolledProjects";
    public static final String ADD_ALL_TECHNOLOGY = "/project/addAll";
    public static final String DELETE_ALL_TECHNOLOGY = "/project/deleteAll";
    public static final String GET_TECHNOLOGY_ID_BY_NAME = "/project/getIdByName/";
    public static final String TECHNOLOGY_FOLLOWED_BY_USER = "/project/followedByUser";
    public static final String TECHNOLOGY_UNFOLLOWED_BY_USER = "/project/unFollowedByUser";
    public static final String GET_ALL_FOLLOWED_EVENTS_TECHNOLOGY = "/project/getAllFollowedEventsProject";
    public static final String GET_MY_FOLLOWED_TECHNOLOGIES = "/project/getMyFollowedProjects";
    public static final String MY_FOLLOWED_TECHNOLOGIES = "/project/myFollowedProjects";
    public static final String CHECK_TECHNOLOGY_EXIST = "/project/checkProjectExist/";
    public static final String ADD_ONE_FOLLOWER_TECHNOLOGY = "/project/addOneFollower/";
    public static final String DELETE_ONE_FOLLOWER_TECHNOLOGY = "/project/deleteOneFollower/";
    public static final String GET_FOLLOWERS_FOR_TECHNOLOGY = "/project/followers/";
    public static final String GET_ALL_POPULAR_TECHNOLOGIES = "/project/allPopularProjects";
    public static final String ADD_POPULAR_TECHNOLOGIES = "/project/addPopularProject";

    /*================================================= Technologies =================================================*/


    /*================================================= Jobs =========================================================*/
    public static final String JOB_REGISTER_POST = "/job/addJob";
    public static final String GET_JOB_BY_ID = "/job/jobDetail/";
    public static final String GET_JOB_APPLICATION_BY_ID = "/job/jobApplicationDetail/";
    public static final String JOB_EDIT_POST = "/job/updateJob/";
    public static final String DELETE_JOB_IMAGE = "/job/deleteJobImage/";
    public static final String DELETE_JOB_PDF = "/job/deleteJobPDF/";
    public static final String JOB_LIST = "/job/jobList/";
    public static final String JOB_APPLY_POST = "/job/applyJob/";
    public static final String JOB_STATUS_UPDATE = "/job/updateStatus/";

    public static final String JOB_POSTED_BY_USER = "/job/getJobsByUser/";

    public static final String JOB_APPLIED_BY_USER = "/job/getJobsByApplicant/";

    public static final String GET_JOBS_BY_CONDITION = "/job/search/conditions";
    public static final String SET_JOB_PDF = "/job/setPDF/";
    public static final String DELETE_JOB_BY_ID = "/job/deleteJob/";
    //public static final String GET_JOB_BY_PUBLISHER = "/job/getJobByPublisher/";
    public static final String CHECK_JOB_NAME = "/job/isJobNameExisted";

    public static final String SAVE_JOB_IMG = "/job/saveJobImage/";
    public static final String GET_JOB_ID_BY_NAME = "/job/getIdByName/";

    public static final String CHECK_JOB_EXIST = "/job/checkJobExist/";

    public static final String STR_BACKEND_URL_JOB_APPLICATIONS = "/job/getApplications/";
    public static final String JOB_APPLICATIONS_BY_USER ="/job/getApplicationsByUser/";

    /*================================================= Jobs =========================================================*/

    /*================================================= RAJobs =======================================================*/
    public static final String RAJOB_REGISTER_POST = "/rajob/addRAJob";
    public static final String GET_RAJOB_BY_ID = "/rajob/rajobDetail/";
    public static final String RAJOB_EDIT_POST = "/rajob/updateRAJob/";
    public static final String GET_RAJOB_APPLICATION_BY_ID = "/rajob/rajobApplicationDetail/";
    public static final String RAJOB_STATUS_UPDATE = "/rajob/updateStatus/";
    public static final String RAJOB_APPLICATION_STATUS_UPDATE = "/rajob/updateRAjobApplicationStatus/";
    public static final String DELETE_RAJOB_IMAGE = "/rajob/deleteRAJobImage/";
    public static final String DELETE_RAJOB_PDF = "/rajob/deleteRAJobPDF/";
    public static final String RAJOB_LIST = "/rajob/rajobList/";
    public static final String RAJOB_APPLY_POST = "/rajob/applyRAJob/";
    public static final String RAJOB_POSTED_BY_USER = "/rajob/getRAJobsByUser/";

    public static final String RAJOB_APPLIED_BY_USER = "/rajob/getRAJobsByApplicant/";

    public static final String GET_RAJOBS_BY_CONDITION = "/rajob/search/conditions";
    public static final String SET_RAJOB_PDF = "/rajob/setPDF/";
    public static final String DELETE_RAJOB_BY_ID = "/rajob/deleteRAJob/";
    public static final String CLOSE_RAJOB_BY_ID = "/rajob/closeRAJob/";
    //public static final String GET_RAJOB_BY_PUBLISHER = "/rajob/getRAJobByPublisher/";
    public static final String CHECK_RAJOB_NAME = "/rajob/isRAJobNameExisted";

    public static final String SAVE_RAJOB_IMG = "/rajob/saveRAJobImage/";
    public static final String GET_RAJOB_ID_BY_NAME = "/rajob/getIdByName/";

    public static final String CHECK_RAJOB_EXISTSTR_BACKEND_URL_JOB_APPLICATIONS = "/job/checkJobExist/";
    /*================================================= RAJobs =======================================================*/

    /*================================================= RA Job Interviews ==============================================*/
    public static final String SCHEDULE_INTERVIEW              = "/rajobInterview/schedule";
    public static final String GET_INTERVIEW_BY_APPLICATION_ID = "/rajobInterview/getByApplicationId/";
    public static final String GET_INTERVIEW_BY_ID             = "/rajobInterview/getById/";
    public static final String GET_INTERVIEWS_BY_APPLICANT     = "/rajobInterview/getByApplicant/";
    public static final String GET_INTERVIEWS_BY_PUBLISHER     = "/rajobInterview/getByPublisher/";
    public static final String CANCEL_INTERVIEW                = "/rajobInterview/cancel/";
    /*================================================= RA Job Interviews ==============================================*/

    /*================================================= Faculty Profiles ===============================================*/
    public static final String GET_ALL_FACULTY_PROFILES = "/faculty/profiles";
    public static final String GET_FACULTY_PROFILE      = "/faculty/profile/";
    /*================================================= Faculty Profiles ===============================================*/

    /*================================================= TACandidate =======================================================*/

    public static final String TACANDIDATE_REGISTER_POST = "/tacandidate/addTACandidate";

    public static final String TACANDIDATE_LIST = "/tacandidate/tacandidateList/";

    public static final String GET_TACANDIDATE_BY_ID = "/tacandidate/candidateDetail/";

    public static final String GET_ASSIGNMENTS_BY_USER_ID = "/tacandidate/assignments/";

    /*================================================= TACandidate =======================================================*/

    /*================================================= Course =======================================================*/

    public static final String COURSE_LIST = "/courses";

    /*================================================= Course =======================================================*/



    /*================================================= Course TA Assignments =======================================================*/

    public static final String TA_HIRING_STATUS_LIST = "/tahiring/assignmentList/";

    public static final String GET_TAASSIGNMENT_BY_ID = "/tahiring/assignmentDetail/";

    public static final String ASSIGNMENT_REGISTER_POST = "/tahiring/addAssignment";

    /*================================================= Course TA Assignments =======================================================*/

    /*================================================= TAJobs =======================================================*/
    public static final String TAJOB_REGISTER_POST = "/tajob/addTAJob";
    public static final String GET_TAJOB_BY_ID = "/tajob/tajobDetail/";
    public static final String TAJOB_EDIT_POST = "/tajob/updateTAJob/";
    public static final String GET_TAJOB_APPLICATION_BY_ID = "/tajob/tajobApplicationDetail/";
    public static final String TAJOB_STATUS_UPDATE = "/tajob/updateStatus/";
    public static final String DELETE_TAJOB_IMAGE = "/tajob/deleteTAJobImage/";
    public static final String DELETE_TAJOB_PDF = "/tajob/deleteTAJobPDF/";
    public static final String TAJOB_LIST = "/tajob/tajobList/";
    public static final String TAJOB_APPLY_POST = "/tajob/applyTAJob/";
    public static final String TAJOB_POSTED_BY_USER = "/tajob/getTAJobsByUser/";
    public static final String GET_TAJOBS_BY_CONDITION = "/tajob/search/conditions";
    public static final String SET_TAJOB_PDF = "/tajob/setPDF/";
    public static final String DELETE_TAJOB_BY_ID = "/tajob/deleteTAJob/";
    //public static final String GET_RAJOB_BY_PUBLISHER = "/rajob/getRAJobByPublisher/";
    public static final String CHECK_TAJOB_NAME = "/tajob/isTAJobNameExisted";

    public static final String SAVE_TAJOB_IMG = "/tajob/saveTAJobImage/";
    public static final String GET_TAJOB_ID_BY_NAME = "/tajob/getIdByName/";

    public static final String CHECK_TAJOB_EXIST = "/job/checkJobExist/";
    /*================================================= TAJobs =======================================================*/



    /*================================================= Access =======================================================*/
    public static final String ACCESS_TIME_PLUS_ONE = "/access/addOneAccess";
    /*================================================= Access =======================================================*/

    /*================================================= Authors ======================================================*/
    public static final String AUTHOR_REGISTER_POST = "/author/addAuthor";
    public static final String AUTHOR_DETAIL = "/author/authorDetail/";
    public static final String AUTHOR_EDIT_POST = "/author/updateUser";
    public static final String AUTHOR_EDIT_POST_ADMIN = "/author/updateUserAdmin";


    public static final String AUTHOR_LIST = "/author/authorList";
    public static final String USER_LIST_ADMIN = "/author/userListAdmin";
    public static final String USER_DETAIL_ADMIN = "/author/userDetailAdmin/";


    public static final String GET_AUTHOR_PROFILE_BY_ID = "/author/authorDetail/";
    public static final String GET_AUTHOR_PROFILE_BY_ID_ADMIN = "/author/authorDetailAdmin/";


    public static final String GET_AUTHOR_IMAGE_BY_AUTHORID = "/author/getUserImageByUserId/";
    // This is for front-end image path
    public static final String AUTHOR_IMAGE_BY_IMAGE_ID_PATH = "/author/authorImageByImageId/";
    // This is for backend routing
    public static final String GET_AUTHOR_IMAGE_BY_IMAGE_ID = "/author/authorImageByImageId/";
    public static final String GET_AUTHOR_BY_CONDITION = "/author/search";
    public static final String AUTHOR_DELETE = "/author/authorDelete";



    public static final String AUTHOR_REGISTER_AUTO = "/author/autoRegisterUser";

    public static final String GET_ALL_AUTHORS = "/author/getAllUsers";

    public static final String AUTHOR_DELETE_IMAGE = "/author/deleteImageForUser/";
    public static final String AUTHOR_UPDATE_IMAGE = "/author/updateImageForUser/";




    public static final String AUTHOR_FOLLOWED_BY_USER = "/author/followedByUser";
    public static final String AUTHOR_UNFOLLOWED_BY_USER = "/author/unFollowedByUser";

    public static final String ADD_ALL_AUTHORS = "/author/addAll";


    /*================================================= Authors ======================================================*/
    /*================================================= Reviewers ====================================================*/
    public static final String REVIEWER_REGISTER_POST = "/reviewer/addReviewer";
    public static final String REVIEWER_DETAIL = "/reviewer/reviewerDetail/";
    public static final String REVIEWER_EDIT_POST = "/reviewer/updateReviewer";

    public static final String REVIEWER_LIST = "/reviewer/reviewerList";


    public static final String GET_REVIEWER_PROFILE_BY_ID = "/reviewer/reviewerDetail/";


    public static final String GET_REVIEWER_IMAGE_BY_AUTHORID = "/reviewer/getReviewersImageByUserId/";
    // This is for front-end image path
    public static final String REVIEWER_IMAGE_BY_IMAGE_ID_PATH = "/reviewer/reviewerImageByImageId/";
    // This is for backend routing
    public static final String GET_REVIEWER_IMAGE_BY_IMAGE_ID = "/reviewer/reviewerImageByImageId/";
    public static final String GET_REVIEWER_BY_CONDITION = "/reviewer/search/conditions";
    public static final String REVIEWER_DELETE = "/reviewer/reviewerDelete";



    public static final String REVIEWER_REGISTER_AUTO = "/reviewer/autoRegisterReviewer";

    public static final String GET_ALL_REVIEWERS = "/reviewer/getAllReviewers";

    public static final String REVIEWER_DELETE_IMAGE = "/reviewer/deleteImageForUser/";
    public static final String REVIEWER_UPDATE_IMAGE = "/reviewer/updateImageForUser/";


    public static final String ADD_ALL_REVIEWERS= "/reviewer/addAll";


    /*================================================= Papers =======================================================*/

    public static final String PAPER_REGISTER_POST= "/paper/addPaper";

    public static final String PAPER_LIST= "/paper/paperList";
    public static final String GET_AUTHORS_BY_PAPER_ID= "/paper/author";
    public static final String DELETE_PAPER_BY_ID= "/paper/deletePaper";

    public static final String GET_PAPER_BY_ID = "/paper/paperDetail/";
    public static final String ADD_AUTHOR_TO_PAPER = "/paper/addAuthor/";
    public static final String DELETE_AUTHOR_MEMBER = "/paper/deleteAuthor";

    public static final String GET_PRIME_CONNECTIONS= "/graph/primePOC";

    public static final String AUTHOR_PAPER_RELATION=    "/paper/paperAuthorList";
    public static final String POST_PAPER_FROM_DBLP = "/paper/loadPaper";
    public static final String LDA_TOPIC = "/paper/lda";

    public static final String GET_AUTHOR_AUTHOR_REL = "/paper/authorRelation";
    public static final String LOAD_SCHEMA = "/paper/loadSchema";
    public static final String LDA_TOPIC_LIST = "/paper/ldaTopicList";
    public static final String GET_TOP_AUTHORS = "/author/topAuthors";
    public static final String PAPER_SEARCH = "/paper/search";

    /*================================================= Log =======================================================*/
    public static final String STR_OPERATION_LOGGING = "/log/loggingOperation";
    public static final String CHALLENGE_SEARCH_API = "/challenge/search";

    public static final String FILE = "/file/";
    public static final String FILE_UPLOAD_ENDPOINT = "/file/upload";
    public static final String CHECK_FILE = "/file/checkFile/";



    public static enum USER_TYPE {
        GENERAL(0), RESEARCHER(1), SPONSOR(2), STUDENT(4);

        private int value = 0;
        private USER_TYPE(int value) {this.value = value;}

        public static USER_TYPE valueOf(int value) {
            switch(value) {
                case 0: return GENERAL;
                case 1: return RESEARCHER;
                case 2: return SPONSOR;
                case 4: return STUDENT;
                default: return null;
            }
        }

        public int value() {
            return this.value;
        }
    };
}
