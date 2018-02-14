(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'frontend'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'frontend'.");
    }
    root.frontend = factory(typeof frontend === 'undefined' ? {} : frontend, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  function main(args) {
    println('Hello klaudia!');
  }
  var package$index = _.index || (_.index = {});
  package$index.main_kand9s$ = main;
  main([]);
  return _;
}));

//# sourceMappingURL=frontend.js.map
