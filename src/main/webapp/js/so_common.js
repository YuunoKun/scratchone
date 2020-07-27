function ajaxRequestJSON(url, type, data, success) {
    $.ajax({
        url: url,
        type: type,
        dataType: "json",
        data: data,
        headers: {"so_token":localStorage.so_token},
        success: success
    })
}

function ajaxRequestObj(url, type, data, success) {
    $.ajax({
        url: url,
        type: type,
        dataType: "json",
        data: data,
        headers: {"so_token":localStorage.so_token},
        success: success
    })
}