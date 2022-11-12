package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/laps")
@RequiredArgsConstructor
class LapController {

    private final LapFacade lapFacade;
}
