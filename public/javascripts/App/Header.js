/**
 * Created by abhijeetd on 11/21/14.
 */
function setupHeader() {
    if (userLoggedIn()) {
        $("#squid-header-user-header-dropdown-menu").toggleClass("hidden");
        $.ajax({
            type: 'GET',
            url: '/api/user/' + userId,
            success: function (response) {
                $("#username-dropdown").html(response.firstName + ' <span class="caret"></span>');
                $("#user-profile-image").attr('src', response.imageUrl);
            }
        });
    } else {
        $("#squid-header-user-signup-dropdown-menu").toggleClass("hidden");
        $("#squid-signup-form").toggleClass("hidden");
    }
}