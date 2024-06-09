import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { SingleEliminationBracket, Match, SVGViewer } from '@g-loot/react-tournament-brackets';
import useFetch from '../hooks/useFetch';
import config from '../config';

function TournamentBracket() {
  const [matches, setMatches] = useState(null);
  const { weightCategory } = useParams();
  const {
    data: duels,
    isPending,
    error
  } = useFetch(`${config.backendUrl}/duels/${weightCategory}`);

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
    if(duels != null){
      console.log(duels);
      /*
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
        if(duel.winner === -1) {
          match.state = 'SCHEDULED';
        } else {
          match.state = 'SCORE_DONE';
        }
        match.participants = [];

        if(duel.idContestant1 != null) {
          match.participants.push(addParticipant(duel.idContestant1, duel.winner, duel.name1, duel.score1));
        }
        if(duel.idContestant2 != null) {
          match.participants.push(addParticipant(duel.idContestant2, duel.winner, duel.name2, duel.score2));
        }

        matchesList.push(match);
      }
      setMatches(matchesList);*/

    }

  }, [duels]);

  return (
    <>
      {matches && (
        <SingleEliminationBracket
          matches={matches}
          matchComponent={Match}
          svgWrapper={({ children, ...props }) => (
            <SVGViewer width={500} height={500} {...props}>
              {children}
            </SVGViewer>
          )}
        />
      )}
    </>
  );
}

export default TournamentBracket;
