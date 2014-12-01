/**
 * Created by abhijeetd on 11/24/14.
 */
function setupBody() {
//    if (userLoggedIn()) {
//
//    } else {
    $.ajax({
        type: 'GET',
        url: '/api/automobiles/page/1',
        success: function (response) {
            $.each(response, function (i, item) {
                $("#automobiles").append('<li>' + createItemHtml(item) + '</li>')
            })
        }
    });
//    }
}

$(document).on('mouseover', '.gallery li img', function () {
    var $gallery = $(this).parents('.gallery');
    $('.main-img', $gallery).attr('src', $(this).attr('src'));
});


function createItemHtml(item) {
    var itemHtml = '<div class="post">' +
        '<div class="name-description">' +
        '<p class="title">' + item.title + '</p>' +
        '<p class="description">' + item.description + '</p>' +
    '</div>' +
    '<div class="gallery">' +
    '<div class="thumbnails">' +
    '<ul>';
    if(item.contents.length) {
        jQuery.each(item.contents, function (index, content) {
            itemHtml += '<li><img src="' + content.url + '" alt=""/></li>';
        });
    }
    itemHtml += '</ul></div>';
    if(item.contents.length) {
        itemHtml += '<img src="' + item.contents[0].url + '" alt="" class="main-img"/>';
    }
    itemHtml += '</div>' +  // end gallery div
        '</div>';   //end post div

    return itemHtml;
}