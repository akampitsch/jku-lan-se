package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.ArrivalAction;
import at.jku.oeh.lan.laganizer.model.ArrivalLog;
import at.jku.oeh.lan.laganizer.model.ArrivalLogDAO;
import at.jku.oeh.lan.laganizer.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.io.Serializable;
import java.util.Calendar;

@RestController
@RequestMapping("/arrivalLog/")
@PermitAll
public class ArrivalLogController {

    @Autowired
    private ArrivalLogDAO arrivalLogDAO;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO<>((Serializable) arrivalLogDAO.findAll(), true);
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<ArrivalLog> create(@RequestParam long userId,
                                                 @RequestParam int action,
                                                 @RequestParam long reporterId,
                                                 @RequestParam String note) {
        RESTDataWrapperDTO<ArrivalLog> result = new RESTDataWrapperDTO<>();
        ArrivalLog arrivalLog = new ArrivalLog();
        //todo check if all tose are valid assignments & if users exits, ...
        arrivalLog.setUser(userDAO.findOne(userId));
        arrivalLog.setAction(ArrivalAction.values()[action]);
        arrivalLog.setTimestamp(Calendar.getInstance());
        arrivalLog.setReporter(userDAO.findOne(reporterId));
        arrivalLog.setNote(note);
        arrivalLogDAO.save(arrivalLog);

        result.setData(arrivalLog);
        result.setSuccess(true);
        return result;
    }
}
