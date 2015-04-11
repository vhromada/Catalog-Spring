$(document).ready(function () {
  $('.error:not(:empty)').each(function () {
    $(this).parent().parent().addClass('has-error');
  });
});