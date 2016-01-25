$(document).ready(function() {

  $('#search-button').click(function() {
    var query = $('#search-query').val();

    $.ajax({
      url: '/address',
      dataType: 'json',
      data: {'query': query},
      success: function(data, status, req) {
        console.log(data.results);
        var searchResult = document.getElementById('search-result');
        searchResult.innerHTML = "";

        // Make list
        for (i=0; i<data.results.length; i++) {
          var shop = data.results[i];

          var list = document.createElement('a');
          list.classList.add('result-item');
          list.href="#";
          list.innerHTML = shop.name + " | " + shop.formatted_address;

          list.dataset.shopname = shop.name;
          list.dataset.address = shop.formatted_address;
          list.dataset.lon = shop.geometry.location.lng;
          list.dataset.lat = shop.geometry.location.lat;
          list.onclick = function() {
            document.getElementById('address').value = this.dataset.address;
            document.getElementById('shopname').value = this.dataset.shopname;
            document.getElementById('lon').value = this.dataset.lon;
            document.getElementById('lat').value = this.dataset.lat;
            $("#search-address").modal('toggle');
          }

          searchResult.appendChild(list);
        }

      },
      error: function(req, status, data) {
        console.log(data);
      }});
  });

});
