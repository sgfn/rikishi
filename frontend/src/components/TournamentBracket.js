import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import { SingleEliminationBracket, Match, SVGViewer } from '@g-loot/react-tournament-brackets';
import useWindowSize from "@g-loot/react-tournament-brackets/dist/cjs/hooks/use-window-size";
import useFetch from '../hooks/useFetch';
import config from '../config';
import './TournamentBracket.css';
import exitIcon from "../../assets/icons/exit.png";

function TournamentBracket() {
  const [matches, setMatches] = useState(null);
  const [duels, setDuels] = useState([]);
  const { weightCategory } = useParams();
  const history = useNavigate();
  const {
    data,
    isPending,
    error
  } = useFetch(`${config.backendUrl}/duels/${weightCategory}`);

  const handleExit = () => {
    console.log('Exit button pressed');
    history(`/contestantsWeight/${weightCategory}`);
  };

  const handleGoBack = () => {
    history(`/`,{state:{bracketSubmitted: true, weightCategory: weightCategory}});
  };

  function addParticipant(id, winner, name, score) {
    const participant = {};
    participant.id = id;
    participant.name = name;
    participant.resultText = score;

    if(winner === -1) {
      participant.isWinner = false;
      participant.status = null;
    } else if(winner === participant.id) {
      participant.isWinner = true;
      participant.status = 'PLAYED';
    } else {
      participant.isWinner = false;
      participant.status = 'PLAYED';
    }

    return participant;
  }

  // zakładam że maksymalnie do 16 zawodników jest turniej
  function roundName(id) {
    switch (id) {
      case 0:
        return 'Final';
      case 1:
      case 2:
        return 'Semi-Final';
      case 3:
      case 4:
      case 5:
      case 6:
        return 'Quarter-Final';
      default:
        return '1 Round';
    }
  }

  // zakładam że maksymalnie do 16 zawodników jest turniej
  function roundNumber(id, length) {
    if (id === 0) {
      if (length >= 8) {
        return '4';
      }
      return '3';
    }
    if (id <= 2) {
      if (length >= 8) {
        return '3';
      }
      return '2';
    }
    if (id <= 6) {
      if (length >= 8) {
        return '2';
      }
      return '1';
    }
    return '1';
  }

  // ten hook służy do aktualizowania matches
  useEffect(() => {
    if (duels.length > 0) {
      const matchesList = [];

      for (const duel of duels) {
        const match = {};
        match.id = duel.id;
        match.name = roundName(match.id);
        if(match.id === 0) {
          match.nextMatchId = null;
        } else {
          match.nextMatchId = Math.floor((match.id - 1) / 2);
        }
        match.tournamentRoundText = roundNumber(match.id, duels.length);
        const todayDate = new Date();
        match.startTime = `${todayDate.getDate()}-${todayDate.getMonth()}-${todayDate.getFullYear()}`;
        if(duel.winnerId === -1) {
          match.state = 'SCHEDULED';
        } else {
          match.state = 'SCORE_DONE';
        }
        match.participants = [];

        if(duel.id1Contestant != null) {
          match.participants.push(addParticipant(duel.id1Contestant, duel.winnerId, duel.name1, duel.score1));
        }
        if(duel.id2Contestant != null) {
          match.participants.push(addParticipant(duel.id2Contestant, duel.winnerId, duel.name2, duel.score2));
        }
        console.log(match);
        matchesList.push(match);
      }
      setMatches(matchesList);
    }
  }, [duels]);

  useEffect(() => {
    if (data != null) {
      console.log(data);
      setDuels(data.duels);
    }
  }, [data]);

  const [width, height] = useWindowSize();

  return (
    <>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" className="exit-icon"/>
      </button>
      <h2>Tournament Bracket</h2>
      <div className="tournament-bracket">

        {matches && (
          <SingleEliminationBracket
            matches={matches}
            matchComponent={Match}
            svgWrapper={({children, ...props}) => (
              <SVGViewer width={width} height={height} {...props}>
                {children}
              </SVGViewer>
            )}
          />
        )}
      </div>
      <button className="button" type="button" onClick={handleGoBack}>
        Save tournament ladder
      </button>
    </>
  );
}

export default TournamentBracket;