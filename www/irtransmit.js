var irtransmit = {
    transmit: function(frequency, pattern, successCallback, errorCallback) {
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "transmit",
	    [{
		"frequency": frequency,
		"pattern": pattern
	    }]
	);
    },

    hasIrEmitter: function(successCallback, errorCallback) {
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "hasIrEmitter",
	    [{
	    }]
	);
    },

    getCarrierFrequencies: function(successCallback, errorCallback) {
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "getCarrierFrequencies",
	    [{
	    }]
	);
    }
}

module.exports = irtransmit;
